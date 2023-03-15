package com.hani.realworld.user.adapter.out.persistence;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.common.util.PasswordEncoderUtil.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.hani.realworld.user.domain.Followees;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

// @Transactional
@DataJpaTest
// @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({UserPersistenceAdapter.class, UserMapper.class})
class UserPersistenceAdapterTest {

	@Autowired
	private UserPersistenceAdapter adapter;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProfileRepository profileRepository;

	@Sql(value = "UserPersistenceAdapterTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void load_user_with_id_succeeds() {
		// when
		User user = adapter.loadUserWithId(USER2.getId());

		// then
		assertThat(user.getUsername()).isEqualTo("username2");
		assertThat(user.getEmail()).isEqualTo("user2@naver.com");
	}

	@Sql(value = "UserPersistenceAdapterTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void load_user_with_email_succeeds() {
		// when
		User user = adapter.loadUserWithEmail(USER2.getEmail());

		// then
		assertThat(user.getUsername()).isEqualTo("username2");
		assertThat(user.getEmail()).isEqualTo("user2@naver.com");
	}

	@Test
	void register_User_succeeds() {
		// given
		User user = defaultUser().withUserId(null).build();

		// when
		adapter.register(user);

		// then
		assertThat(userRepository.count()).isEqualTo(1);

		UserJpaEntity savedEntity = userRepository.findAll().get(0);

		assertThat(savedEntity.getUsername()).isEqualTo(user.getUsername());
		assertThat(savedEntity.getEmail()).isEqualTo(user.getEmail());
		assertThat(savedEntity.getBio()).isEqualTo(user.getBio());
		assertThat(savedEntity.getImage()).isEqualTo(user.getImage());

		verifyPassword(savedEntity, "password");
	}

	@Sql(value = "UserPersistenceAdapterTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void register_Profile_succeeds() {
		// given
		Profile profile = defaultProfile()
			.withProfileId(null)
			.withUser(USER1)
			.withFollowees(new Followees())
			.build();

		// when
		adapter.register(profile);

		// then
		assertThat(profileRepository.count()).isEqualTo(1);

		ProfileJpaEntity savedEntity = profileRepository.findAll().get(0);
		assertThat(savedEntity.getUser().getId()).isEqualTo(USER1.getId().getValue());
	}

	@Sql(value = "UserPersistenceAdapterTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void update_User_succeeds() {
		// given
		final String updatePassword = "updatePassword";

		User updatedUser = defaultUser()
			.withUserId(USER1.getId())
			.withUsername(USER2.getUsername())
			.withEmail(USER2.getEmail())
			.withPassword(updatePassword)
			.withBio(USER2.getBio())
			.withImage(USER2.getImage())
			.build();

		// when
		adapter.updateUserState(updatedUser);

		// then
		UserJpaEntity savedEntity = userRepository.findById(USER1.getId().getValue()).get();
		assertThat(savedEntity.getUsername()).isEqualTo(USER2.getUsername());
		assertThat(savedEntity.getEmail()).isEqualTo(USER2.getEmail());
		assertThat(savedEntity.getBio()).isEqualTo(USER2.getBio());
		assertThat(savedEntity.getImage()).isEqualTo(USER2.getImage());

		verifyPassword(savedEntity, updatePassword);
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void load_profile_with_userId_succeeds() {
		// when
		Profile profile = adapter.loadProfileWithUserId(USER1.getId());

		// then
		assertThat(profile.getId()).isEqualTo(PROFILE1.getId());
		assertThat(profile.getUser().getId()).isEqualTo(USER1.getId());
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void load_profile_with_username_succeeds() {
		// when
		Profile profile = adapter.loadProfileWithUsername(USER1.getUsername());

		// then
		assertThat(profile.getId()).isEqualTo(PROFILE1.getId());
		assertThat(profile.getUser().getId()).isEqualTo(USER1.getId());
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void update_followees_succeeds() {
		// given
		Profile profile = adapter.loadProfileWithUsername(USER1.getUsername());
		profile.follow(USER2);

		// when
		adapter.updateProfile(profile);

		// then
		// assertThat(profile.getId()).isEqualTo(PROFILE1.getId());
		// assertThat(profile.getUser().getId()).isEqualTo(USER1.getId());
		assertThat(profile.getFollowees().isFollow(USER2)).isTrue();
	}

	@Sql(value = {"UserPersistenceAdapterTest.sql", "ProfilePersistenceAdapterTest.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void load_profile_with_profileId_succeeds() {
		// when
		Profile profile = adapter.loadProfileWithProfileId(PROFILE1.getId());

		// then
		assertThat(profile.getUser().getId()).isEqualTo(USER1.getId());
	}

}

package com.hani.realworld.user.adapter.out.persistence;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.common.util.PasswordEncoderUtil.*;
import static com.hani.realworld.user.domain.User.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({UserPersistenceAdapter.class, UserMapper.class})
class UserPersistenceAdapterTest {

	@Autowired
	private UserPersistenceAdapter adapter;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@DisplayName("유저 ID로 조회 영속성 테스트 - 성공, adpater.loadUserWithId(userId)")
	@Sql(value = "UserPersistenceAdapterTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void load_user_with_id_succeeds() {
		// when
		User user = adapter.loadUserWithId(USER2.getId());

		// then
		assertThat(user.getUsername()).isEqualTo("username2");
		assertThat(user.getEmail()).isEqualTo("user2@naver.com");
	}

	@DisplayName("유저 이메일로 조회 영속성 테스트 - 성공, adpater.loadUserWithEmail(userId)")
	@Sql(value = "UserPersistenceAdapterTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void load_user_with_email_succeeds() {
		// when
		User user = adapter.loadUserWithEmail(USER2.getEmail());

		// then
		assertThat(user.getUsername()).isEqualTo("username2");
		assertThat(user.getEmail()).isEqualTo("user2@naver.com");
	}

	@DisplayName("유저 생성 영속성 테스트 - 성공, adpater.register(user)")
	@Test
	void register_User_succeeds() {
		// given
		User user = defaultUser()
			.withUserId(new UserId(1L))
			.withUsername(USER1.getUsername())
			.withEmail(USER1.getEmail())
			.withBio(USER1.getBio())
			.withImage(USER1.getImage())
			.build();

		// when
		adapter.register(user);

		// then
		assertThat(userRepository.count()).isEqualTo(1);

		UserJpaEntity savedEntity = userRepository.findAll().get(0);

		assertThat(savedEntity.getUsername()).isEqualTo(USER1.getUsername());
		assertThat(savedEntity.getEmail()).isEqualTo(USER1.getEmail());
		assertThat(savedEntity.getBio()).isEqualTo(USER1.getBio());
		assertThat(savedEntity.getImage()).isEqualTo(USER1.getImage());

		verifyPassword(savedEntity, "password");
	}

	@DisplayName("프로필 생성 영속성 테스트 - 성공, adpater.register(profile)")
	@Sql(value = "UserPersistenceAdapterTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void register_Profile_succeeds() {
		// given
		Profile profile = defaultProfile()
			.withProfileId(null)
			.withUser(USER1)
			.withFollowees(PROFILE1.getFollowees())
			.build();

		// when
		adapter.register(profile);

		// then
		assertThat(profileRepository.count()).isEqualTo(1);

		ProfileJpaEntity savedEntity = profileRepository.findAll().get(0);
		assertThat(savedEntity.getUser().getId()).isEqualTo(USER1.getId().getValue());
		assertThat(savedEntity.getFollowees()).contains(USER2.getId().getValue()).size().isEqualTo(1);
	}

	@DisplayName("유저 정보 수정 영속성 테스트 - 성공, adpater.updateUserState(user)")
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
			.withImage(USER2.getImage())
			.withBio(USER2.getBio())
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

	@DisplayName("유저 ID로 프로필 조회 영속성 테스트 - 성공, adpater.loadProfileWithUserId(userId)")
	@Sql(
		value = {"UserPersistenceAdapterTest.sql", "ProfilePersistenceAdapterTest.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
	)
	@Test
	void load_profile_with_userId_succeeds() {
		// when
		Profile profile = adapter.loadProfileWithUserId(USER1.getId());

		// then
		assertThat(profile.getId()).isEqualTo(PROFILE1.getId());
		assertThat(profile.getUser().getId()).isEqualTo(USER1.getId());
	}

	@DisplayName("유저 이름으로 프로필 조회 영속성 테스트 - 성공, adpater.loadProfileWithUsername(userId)")
	@Sql(
		value = {"UserPersistenceAdapterTest.sql", "ProfilePersistenceAdapterTest.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
	)
	@Test
	void load_profile_with_username_succeeds() {
		// when
		Profile profile = adapter.loadProfileWithUsername(USER1.getUsername());

		// then
		assertThat(profile.getId()).isEqualTo(PROFILE1.getId());
		assertThat(profile.getUser().getId()).isEqualTo(USER1.getId());
	}

	@DisplayName("프로필 팔로위 수정 영속성 테스트 - 성공, adpater.updateProfile(userId)")
	@Sql(
		value = {"UserPersistenceAdapterTest.sql", "ProfilePersistenceAdapterTest.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
	)
	@Test
	void update_followees_succeeds() {
		// given
		Profile profile = adapter.loadProfileWithUsername(USER1.getUsername());
		profile.follow(USER2);

		// when
		adapter.updateProfile(profile);

		// then
		ProfileJpaEntity result = profileRepository.findById(USER1.getId().getValue()).get();
		assertThat(result.getFollowees()).contains(USER2.getId().getValue());
	}

	@DisplayName("프로필 ID로 프로필 조회 영속성 테스트 - 성공, adpater.loadProfileWithProfileId(profileId)")
	@Sql(
		value = {"UserPersistenceAdapterTest.sql", "ProfilePersistenceAdapterTest.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
	)
	@Test
	void load_profile_with_profileId_succeeds() {
		// when
		Profile profile = adapter.loadProfileWithProfileId(PROFILE1.getId());

		// then
		assertThat(profile.getUser().getId()).isEqualTo(USER1.getId());
	}

}

package com.hani.realworld.user.adapter.out.persistence;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.common.util.PasswordEncoderUtil.*;
import static org.assertj.core.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.hani.realworld.user.domain.User;

@Transactional
@DataJpaTest
// @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({UserPersistenceAdapter.class, UserMapper.class})
class UserPersistenceAdapterTest {

	@Autowired
	private UserPersistenceAdapter adapter;

	@Autowired
	private UserRepository userRepository;

	@Sql(value = "UserPersistenceAdapterTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void load_with_id_succeeds() {
		// when
		User user = adapter.loadUserWithId(new User.UserId(2L));

		// then
		assertThat(user.getUsername()).isEqualTo("username2");
		assertThat(user.getEmail()).isEqualTo("user2@naver.com");
	}

	@Sql(value = "UserPersistenceAdapterTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void load_with_email_succeeds() {
		// when
		User user = adapter.loadUserWithEmail("user2@naver.com");

		// then
		assertThat(user.getUsername()).isEqualTo("username2");
		assertThat(user.getEmail()).isEqualTo("user2@naver.com");
	}

	@ParameterizedTest
	@CsvSource({"username, user@naver.com, password, i'm user, http://image.png"})
	void register_User_succeeds(
		String username,
		String email,
		String password,
		String bio,
		String image) {
		// when
		User user = defaultUser()
			.withUserId(new User.UserId(1L))
			.withUsername(username)
			.withEmail(email)
			.withPassword(password)
			.withBio(bio)
			.withImage(image)
			.build();

		adapter.registerUser(user);

		// then
		assertThat(userRepository.count()).isEqualTo(1);

		UserJpaEntity savedEntity = userRepository.findById(1L).get();
		assertThat(savedEntity.getUsername()).isEqualTo(username);
		assertThat(savedEntity.getEmail()).isEqualTo(email);
		assertThat(savedEntity.getBio()).isEqualTo(bio);
		assertThat(savedEntity.getImage()).isEqualTo(image);

		verifyPassword(savedEntity, password);
	}

	@Sql(value = "UserPersistenceAdapterTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@ParameterizedTest
	@CsvSource({"Updated username, Updated user@naver.com, Updated password, Updated i'm user, http://updatedimage.png"})
	void update_User_succeeds(
		String username,
		String email,
		String password,
		String bio,
		String image) {
		// when
		User updatedUser = defaultUser()
			.withUserId(new User.UserId(1L))
			.withUsername(username)
			.withEmail(email)
			.withPassword(password)
			.withBio(bio)
			.withImage(image)
			.build();

		adapter.updateUserState(updatedUser);

		// then
		UserJpaEntity savedEntity = userRepository.findById(1L).get();
		assertThat(savedEntity.getUsername()).isEqualTo(username);
		assertThat(savedEntity.getEmail()).isEqualTo(email);
		assertThat(savedEntity.getBio()).isEqualTo(bio);
		assertThat(savedEntity.getImage()).isEqualTo(image);

		verifyPassword(savedEntity, password);
	}

}

package com.hani.realworld.user.domain;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.common.exception.UnAuthorizationException;

class UserTest {

	private PasswordEncoder passwordEncoder =
		new BCryptPasswordEncoder();

	@Test
	void encode_and_verify_Password_Succeeds() {
		// given
		User user = defaultUser()
			.withPassword("password")
			.build();

		// when, then
		assertDoesNotThrow(() -> verifyPassword(user, "password"));
	}

	@Test
	void verifyPassword_Failure() {
		// given
		User user = defaultUser()
			.withPassword("password")
			.build();

		// when, then
		assertThrows(UnAuthorizationException.class, () -> verifyPassword(user, "wrongPassword"));
	}

	@Test
	void update_Succeeds() {
		// given
		User user = defaultUser()
			.withBio("defaultBio")
			.withImage("defaultImage").build();

		// when
		User updatedUser = user.update(
			"newUser@naver.com",
			"newUsername",
			"newPassword",
			null,
			null,
			passwordEncoder::encode);

		// then
		assertThat(updatedUser.getEmail()).isEqualTo("newUser@naver.com");
		assertThat(updatedUser.getUsername()).isEqualTo("newUsername");
		assertThat(updatedUser.getBio()).isEqualTo("defaultBio");
		assertThat(updatedUser.getImage()).isEqualTo("defaultImage");

		assertDoesNotThrow(() ->
			passwordEncoder.matches("newPassword", updatedUser.getPassword().getValue()));
	}
}

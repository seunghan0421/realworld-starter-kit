package com.hani.realworld.user.domain;

import static com.hani.realworld.common.data.UserTestData.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.common.exception.UnAuthorizationException;
import com.hani.realworld.common.util.PasswordEncoderUtil;

class UserTest {

	private PasswordEncoder passwordEncoder =
		new BCryptPasswordEncoder();

	@Test
	void encodePassword_Succeeds() {
		// given
		User user = defaultUser()
			.withPassword("password")
			.build();

		// when
		user.encodePassword(passwordEncoder::encode);

		// then
		assertThat(passwordEncoder.matches(
			"password",
			user.getPassword().getValue())).isTrue();
	}

	@Test
	void verifyPassword_Succeeds() {
		// given
		User user = defaultUser()
			.withPassword("password")
			.build();
		PasswordEncoderUtil.encode(user.getPassword());


		// when, then
		assertDoesNotThrow(() -> verifyPassword(user, "password"));
	}

	@Test
	void verifyPassword_Failure() {
		// given
		User user = defaultUser()
			.withPassword("password")
			.build();
		PasswordEncoderUtil.encode(user.getPassword());

		// when, then
		assertThrows(UnAuthorizationException.class, () -> verifyPassword(user, "wrongPassword"));
	}

	private void verifyPassword(User user, String password) {
		user.verifyPassword(encodedPassword ->
			passwordEncoder.matches(
				password,
				encodedPassword.getValue()));
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

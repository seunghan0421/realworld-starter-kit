package com.hani.realworld.user.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordTest {

	private PasswordEncoder passwordEncoder =
		PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Test
	void encode_success() {
		//given
		String value = "password";
		Password password = new Password(value);

		//when
		password.encode(passwordEncoder::encode);

		//then
		boolean result = passwordEncoder.matches(
			value,
			password.getValue());

		assertThat(result).isTrue();
	}

	@Test
	void encode_Failure() {
		//given
		Password password = new Password("rightPassword");

		//when
		password.encode(passwordEncoder::encode);

		//then
		boolean result = passwordEncoder.matches(
			"wrongPassword",
			password.getValue());

		assertThat(result).isFalse();
	}
}

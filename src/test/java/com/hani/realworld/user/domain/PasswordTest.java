package com.hani.realworld.user.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordTest {

	private PasswordEncoder passwordEncoder =
		PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@DisplayName("비밀번호 암호화 도메인 테스트 - 성공, password.encode(passwordEncoder::encode)")
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

	@DisplayName("비밀번호 암호화 도메인 테스트 - 실패, 암호화 된 데이터가 다름,  password.encode(passwordEncoder::encode)")
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

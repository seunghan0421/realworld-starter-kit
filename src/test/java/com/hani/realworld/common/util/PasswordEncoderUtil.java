package com.hani.realworld.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.common.exception.infra.UnAuthorizationException;
import com.hani.realworld.user.adapter.out.persistence.UserJpaEntity;
import com.hani.realworld.user.domain.Password;
import com.hani.realworld.user.domain.User;

public final class PasswordEncoderUtil {

	private static final PasswordEncoder passwordEncoder =
		new BCryptPasswordEncoder();

	public static void encode(Password password) {
		password.encode(passwordEncoder::encode);
	}

	public static void verifyPassword(User user, String password) {
		user.verifyPassword(encodedPassword ->
			passwordEncoder.matches(
				password,
				encodedPassword.getValue()));
	}

	public static void verifyPassword(UserJpaEntity user, String password) {
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new UnAuthorizationException();
		}
	}
}

package com.hani.realworld.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.profile.domain.Password;

public final class PasswordEncoderUtil {

	private static final PasswordEncoder passwordEncoder =
		new BCryptPasswordEncoder();

	public static void encode(Password password) {
		password.encode(passwordEncoder::encode);
	}
}

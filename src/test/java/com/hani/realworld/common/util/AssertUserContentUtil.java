package com.hani.realworld.common.util;

import static org.assertj.core.api.Assertions.*;

import com.hani.realworld.user.application.port.in.result.LoginUserResult;
import com.hani.realworld.user.application.port.in.result.UserResult;

public final class AssertUserContentUtil {

	public static void assertUserContent(UserResult userResult) {
		assertThat(userResult.getEmail()).isNotBlank();
		assertThat(userResult.getUsername()).isNotBlank();
		assertThat(userResult.getBio()).isNotBlank();
		assertThat(userResult.getImage()).isNotBlank();
	}

	public static void assertUserContent(LoginUserResult userResult) {
		assertThat(userResult.getEmail()).isNotBlank();
		assertThat(userResult.getUsername()).isNotBlank();
		assertThat(userResult.getBio()).isNotBlank();
		assertThat(userResult.getImage()).isNotBlank();
	}
}

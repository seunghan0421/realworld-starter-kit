package com.hani.realworld.common.util;

import static org.springframework.http.HttpHeaders.*;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.hani.realworld.common.exception.UnAuthorizationException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Tokens {

	private static String REAL_WORLD_TOKEN = "Token";

	public static String get(HttpServletRequest request) {
		return Optional.of(request.getHeader(AUTHORIZATION))
			.filter(header -> header.startsWith(REAL_WORLD_TOKEN))
			.map(header -> header.substring(REAL_WORLD_TOKEN.length()+ 1))
			.orElseThrow(UnAuthorizationException::new);
	}

	public static boolean isBlankToken(String token) {
		return token.isBlank();
	}
}

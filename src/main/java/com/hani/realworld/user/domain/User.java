package com.hani.realworld.user.domain;

import java.util.function.Function;
import java.util.function.Predicate;

import com.hani.realworld.common.exception.UnAuthorizationException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

	/* 사용자 식별 번호 */
	private final UserId id;

	/* 닉네임 */
	private final String username;

	/* 이메일 */
	private final String email;

	/* 비밀 번호 */
	private final Password password;

	/* 자기 소개 */
	private final String bio;

	/* 이미지 */
	private final String image;

	/**
	 * Creates an {@link User} entity without an ID. Use to create a new entity that is not yet
	 * persisted.
	 */
	public static User withoutId(
		String username,
		String email,
		Password password) {
		return new User(null, username, email, password, null, null);
	}

	/**
	 * Creates an {@link User} entity with an ID. Use to reconstitute a persisted entity.
	 */
	public static User withId(
		UserId userId,
		String username,
		String email,
		Password password,
		String bio,
		String image) {
		return new User(userId, username, email, password, bio, image);
	}

	/**
	 * encrypts the string stored in the value with the input passwordEncoder.
	 */
	public void encodePassword(Function<String, String> passwordEncoder) {
		this.password.encode(passwordEncoder);
	}

	/* 비밀번호 검증 */
	public void verifyPassword(final Predicate<Password> passwordVerifier) {
		if (passwordVerifier.negate().test(this.password)) {
			throw new UnAuthorizationException("비밀번호가 틀립니다.");
		}
	}

	@Value
	public static class UserId {
		private Long value;
	}
}

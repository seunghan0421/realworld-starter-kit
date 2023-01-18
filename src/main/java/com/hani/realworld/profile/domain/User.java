package com.hani.realworld.profile.domain;

import java.util.Optional;
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

	/**
	 * Logic for verifying passwords
	 * @throws UnAuthorizationException if password is not validated
	 */
	public void verifyPassword(Predicate<Password> passwordVerifier) {
		if (passwordVerifier.negate().test(this.password)) {
			throw new UnAuthorizationException("비밀번호가 틀립니다.");
		}
	}

	/**
	 * TODO: 조금 수정할 가치가 있어 보임
	 * Creates an {@link User} entity with an ID. Use to update the persisted entity.
	 */
	public User update(
		String email,
		String username,
		String password,
		String image,
		String bio,
		Function<String, String> passwordEncoder) {
		String updatedEmail = Optional.ofNullable(email).isPresent() ? email : this.email;
		String updatedUsername = Optional.ofNullable(username).isPresent() ? username : this.username;
		String updatedImage = Optional.ofNullable(image).isPresent() ? image : this.image;
		String updatedBio = Optional.ofNullable(bio).isPresent() ? bio : this.bio;

		Password updatedPassword = Optional.ofNullable(password)
			.map(pass -> {
				Password newPassword = new Password(pass);
				newPassword.encode(passwordEncoder);
				return newPassword;
			})
			.orElseGet(() -> this.password);

		return withId(this.id, updatedUsername, updatedEmail, updatedPassword, updatedBio, updatedImage);

	}

	@Value
	public static class UserId {
		private Long value;
	}
}

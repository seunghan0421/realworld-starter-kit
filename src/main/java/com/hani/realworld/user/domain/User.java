package com.hani.realworld.user.domain;

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

	/* User Identification Number */
	private final UserId id;

	/* User's nickname */
	private final String username;

	/* User's email */
	private final String email;

	/* User's password */
	private final Password password;

	/* User's  self-introduction */
	private final String bio;

	/* User's image url */
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
	 *
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
			}).orElseGet(() -> this.password);

		return withId(this.id, updatedUsername, updatedEmail, updatedPassword, updatedBio, updatedImage);

	}

	@Value
	public static class UserId {
		private Long value;
	}
}

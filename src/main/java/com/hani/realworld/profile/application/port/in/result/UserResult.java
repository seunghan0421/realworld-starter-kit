package com.hani.realworld.profile.application.port.in.result;

import com.hani.realworld.profile.domain.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResult {
	private final String email;
	private final String username;
	private final String bio;
	private final String image;

	public static UserResult of(User user) {
		return new UserResult(
			user.getEmail(),
			user.getUsername(),
			user.getBio(),
			user.getImage());
	}
}

package com.hani.realworld.user.application.port.in.result;

import static lombok.AccessLevel.*;

import com.hani.realworld.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
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

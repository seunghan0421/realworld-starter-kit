package com.hani.realworld.user.application.port.in.result;

import com.hani.realworld.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginUserResult {
	private final String email;
	private final String token;
	private final String username;
	private final String bio;
	private final String image;

	public static LoginUserResult of(User user, String token) {
		return new LoginUserResult(
			user.getEmail(),
			token,
			user.getUsername(),
			user.getBio(),
			user.getImage());
	}


}

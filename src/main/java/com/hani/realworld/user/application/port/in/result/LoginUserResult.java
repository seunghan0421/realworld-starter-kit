package com.hani.realworld.user.application.port.in.result;

import com.hani.realworld.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO Getter가 없어도 ParameterBinding이 잘 작동하는지 확인
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

package com.hani.realworld.profile.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.infra.jwt.JwtProvider;
import com.hani.realworld.profile.application.port.in.LoginUserUseCase;
import com.hani.realworld.profile.application.port.in.command.LoginUserCommand;
import com.hani.realworld.profile.application.port.in.result.LoginUserResult;
import com.hani.realworld.profile.application.port.out.LoadUserWithEmailPort;
import com.hani.realworld.profile.application.port.out.LoadUserWithIdPort;
import com.hani.realworld.profile.domain.User;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class LoginUserService implements LoginUserUseCase {

	private final LoadUserWithEmailPort loadUserWithEmailPort;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginUserResult login(LoginUserCommand command) {
		User user = loadUserWithEmailPort.loadUser(command.getEmail());

		user.verifyPassword(
			encodedPassword -> passwordEncoder.matches(command.getPassword(), encodedPassword.getValue()));

		String token = jwtProvider.generate(user.getEmail(), user.getUsername());
		return LoginUserResult.of(user, token);
	}
}

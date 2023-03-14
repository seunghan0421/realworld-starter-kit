package com.hani.realworld.user.application.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.infra.jwt.JwtProvider;
import com.hani.realworld.user.application.port.in.RegisterUserUseCase;
import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;
import com.hani.realworld.user.application.port.out.LoadUserWithEmailPort;
import com.hani.realworld.user.application.port.out.RegisterProfileStatePort;
import com.hani.realworld.user.application.port.out.RegisterUserStatePort;
import com.hani.realworld.user.domain.Password;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

	private final JwtProvider jwtProvider;
	private final RegisterUserStatePort registerUserStatePort;
	private final RegisterProfileStatePort registerProfileStatePort;
	private final LoadUserWithEmailPort loadUserWithEmailPort;
	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginUserResult register(RegisterUserCommand command) {
		User user = User.withoutId(
			command.getUsername(),
			command.getEmail(),
			new Password(command.getPassword()));
		user.encodePassword(passwordEncoder::encode);

		registerUserStatePort.register(user);

		Profile profile = Profile.withoutId(loadUserWithEmailPort.loadUserWithEmail(command.getEmail()));
		registerProfileStatePort.register(profile);

		return LoginUserResult.of(user, jwtProvider.generate(user.getEmail()));
	}
}

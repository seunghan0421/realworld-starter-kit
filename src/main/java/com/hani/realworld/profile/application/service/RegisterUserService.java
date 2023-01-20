package com.hani.realworld.profile.application.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.profile.application.port.in.RegisterUserUseCase;
import com.hani.realworld.profile.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.profile.application.port.in.result.UserResult;
import com.hani.realworld.profile.application.port.out.RegisterUserStatePort;
import com.hani.realworld.profile.domain.Password;
import com.hani.realworld.profile.domain.User;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

	private final RegisterUserStatePort registerUserStatePort;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserResult register(RegisterUserCommand command) {
		User user = User.withoutId(
			command.getUsername(),
			command.getEmail(),
			new Password(command.getPassword()));
		user.encodePassword(passwordEncoder::encode);

		registerUserStatePort.registerUser(user);

		return UserResult.of(user);
	}
}

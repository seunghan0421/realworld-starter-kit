package com.hani.realworld.user.application.service;

import static com.hani.realworld.user.domain.User.*;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.infra.jwt.JwtProvider;
import com.hani.realworld.user.application.port.in.UpdateUserUseCase;
import com.hani.realworld.user.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;
import com.hani.realworld.user.application.port.out.LoadUserWithIdPort;
import com.hani.realworld.user.application.port.out.UpdateUserStatePort;
import com.hani.realworld.user.domain.User;

import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

	private final JwtProvider jwtProvider;
	private final LoadUserWithIdPort loadUserWithIdPort;
	private final UpdateUserStatePort updateUserStatePort;
	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginUserResult updateUser(UpdateUserCommand command, Long userId) {
		User user = loadUserWithIdPort.loadUserWithId(new UserId(userId));

		User updatedUser = user.update(
			command.getEmail(),
			command.getUsername(),
			command.getPassword(),
			command.getImage(),
			command.getBio(),
			passwordEncoder::encode);

		updateUserStatePort.updateUserState(updatedUser);

		return LoginUserResult.of(updatedUser, jwtProvider.generate(updatedUser.getEmail()));
	}
}

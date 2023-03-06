package com.hani.realworld.user.application.service;

import static com.hani.realworld.user.domain.User.*;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.user.application.port.in.UpdateUserUseCase;
import com.hani.realworld.user.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.user.application.port.in.result.UserResult;
import com.hani.realworld.user.application.port.out.LoadUserWithIdPort;
import com.hani.realworld.user.application.port.out.UpdateUserStatePort;
import com.hani.realworld.user.domain.User;

import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

	private final LoadUserWithIdPort loadUserWithIdPort;
	private final UpdateUserStatePort updateUserStatePort;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserResult updateUser(UserId userId, UpdateUserCommand command) {
		User user = loadUserWithIdPort.loadUser(userId);

		User updatedUser = user.update(
			command.getEmail(),
			command.getUsername(),
			command.getPassword(),
			command.getImage(),
			command.getBio(),
			passwordEncoder::encode);

		updateUserStatePort.updateUserState(updatedUser);

		return UserResult.of(updatedUser);
	}
}

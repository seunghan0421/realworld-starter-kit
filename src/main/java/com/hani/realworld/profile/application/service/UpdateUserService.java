package com.hani.realworld.profile.application.service;

import static com.hani.realworld.profile.domain.User.*;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.profile.application.port.in.UpdateUserUseCase;
import com.hani.realworld.profile.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.profile.application.port.in.result.UserResult;
import com.hani.realworld.profile.application.port.out.LoadUserWithIdPort;
import com.hani.realworld.profile.application.port.out.UpdateUserStatePort;
import com.hani.realworld.profile.domain.User;

import lombok.RequiredArgsConstructor;

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

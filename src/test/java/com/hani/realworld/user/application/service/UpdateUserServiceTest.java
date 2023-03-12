package com.hani.realworld.user.application.service;

import static com.hani.realworld.user.domain.User.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.user.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.user.application.port.in.result.UserResult;
import com.hani.realworld.user.application.port.out.LoadUserWithIdPort;
import com.hani.realworld.user.application.port.out.UpdateUserStatePort;
import com.hani.realworld.user.domain.User;

class UpdateUserServiceTest {

	private final LoadUserWithIdPort loadUserWithIdPort =
		Mockito.mock(LoadUserWithIdPort.class);

	private final UpdateUserStatePort updateUserStatePort =
		Mockito.mock(UpdateUserStatePort.class);

	private final PasswordEncoder passwordEncoder =
		new BCryptPasswordEncoder();

	private final UpdateUserService updateUserService =
		new UpdateUserService(loadUserWithIdPort, updateUserStatePort, passwordEncoder);

	@Test
	void updateUserState_Succeeds() {
		// given
		final String updateEmail = "updateuser@email.com";
		final String updateUsername = "updateusername";
		final String updatePassword = "updatepassword";
		final String updateImage = "http://imaupdatege.jpeg";
		final String updateBio = "updatebio";

		UpdateUserCommand command = new UpdateUserCommand(
			updateEmail, updateUsername, updatePassword, updateImage, updateBio);

		UserId userId = new UserId(42L);
		User user = givenLoadUserPortWillSucceeds(userId, command);
		User updatedUser = givenUpdateUserPortWillSucceeds(userId, command, user);

		// when
		UserResult result = updateUserService.updateUser(command, 42L);

		// then
		assertThat(result.getUsername()).isEqualTo(updateUsername);
		assertThat(result.getEmail()).isEqualTo(updateEmail);
		assertThat(result.getImage()).isEqualTo(updateImage);
		assertThat(result.getBio()).isEqualTo(updateBio);

		then(loadUserWithIdPort).should().loadUserWithId(userId);
		then(user).should().update(
			eq(updateEmail),
			eq(updateUsername),
			eq(updatePassword),
			eq(updateImage),
			eq(updateBio),
			any());
		then(updateUserStatePort).should().updateUserState(updatedUser);

	}

	private User givenLoadUserPortWillSucceeds(UserId userId, UpdateUserCommand command) {
		User user = Mockito.mock(User.class);

		given(loadUserWithIdPort.loadUserWithId(eq(userId)))
			.willReturn(user);

		return user;
	}

	private User givenUpdateUserPortWillSucceeds(UserId userId, UpdateUserCommand command, User user) {
		User updateUser = Mockito.mock(User.class);

		given(updateUser.getUsername()).willReturn(command.getUsername());
		given(updateUser.getEmail()).willReturn(command.getEmail());
		given(updateUser.getBio()).willReturn(command.getBio());
		given(updateUser.getImage()).willReturn(command.getImage());

		given(user.update(
			eq(command.getEmail()),
			eq(command.getUsername()),
			eq(command.getPassword()),
			eq(command.getImage()),
			eq(command.getBio()),
			any()))
			.willReturn(updateUser);

		return updateUser;
	}



}

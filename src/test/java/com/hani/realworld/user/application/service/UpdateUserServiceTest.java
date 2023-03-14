package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.common.fixture.UserServiceFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.infra.jwt.JwtProvider;
import com.hani.realworld.user.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;
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
		new UpdateUserService(
			loadUserWithIdPort,
			updateUserStatePort,
			passwordEncoder);

	@Test
	void updateUserState_Succeeds() {
		// given
		User user1 = givenAnUserWithUser1();
		User user2 = givenAnUserWithUser2();

		final String updatedPassword = "updatedPassword";
		UpdateUserCommand command = new UpdateUserCommand(
			user2.getEmail(),
			user2.getUsername(),
			updatedPassword,
			user2.getImage(),
			user2.getBio());

		given(loadUserWithIdPort.loadUserWithId(eq(USER1.getId())))
			.willReturn(user1);
		given(user1.update(
			eq(USER2.getEmail()),
			eq(USER2.getUsername()),
			eq(updatedPassword),
			eq(USER2.getImage()),
			eq(USER2.getBio()), any()))
			.willReturn(user2);

		// when
		UserResult result = updateUserService.updateUser(command, USER1.getId().getValue());

		// then
		assertThat(result.getUsername()).isEqualTo(USER2.getUsername());
		assertThat(result.getEmail()).isEqualTo(USER2.getEmail());
		assertThat(result.getImage()).isEqualTo(USER2.getImage());
		assertThat(result.getBio()).isEqualTo(USER2.getBio());

		then(loadUserWithIdPort).should().loadUserWithId(eq(USER1.getId()));
		then(user1).should().update(
			eq(USER2.getEmail()),
			eq(USER2.getUsername()),
			eq(updatedPassword),
			eq(USER2.getImage()),
			eq(USER2.getBio()), any());
		then(updateUserStatePort).should().updateUserState(any());
	}

}

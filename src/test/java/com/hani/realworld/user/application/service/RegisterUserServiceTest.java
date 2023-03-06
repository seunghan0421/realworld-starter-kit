package com.hani.realworld.user.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.user.application.port.in.result.UserResult;
import com.hani.realworld.user.application.port.out.RegisterUserStatePort;
import com.hani.realworld.user.domain.User;

class RegisterUserServiceTest {

	private final RegisterUserStatePort registerUserStatePort =
		Mockito.mock(RegisterUserStatePort.class);

	private final PasswordEncoder passwordEncoder =
		new BCryptPasswordEncoder();

	private final RegisterUserService registerUserService =
		new RegisterUserService(registerUserStatePort, passwordEncoder);

	// TODO mock static test 해결해야함
	@Disabled("TODO mock static test 해결해야함")
	@Test
	void registerUser_Succeeds() {
		// given
		final String username = "username";
		final String email = "user@email.com";
		final String password = "password";
		RegisterUserCommand command = new RegisterUserCommand(
			username, email, password);

		User user = givenUserWithoutIdWillSucceed(command);

		// when
		UserResult result = registerUserService.register(command);

		// then
		assertThat(result.getUsername()).isEqualTo(username);
		assertThat(result.getEmail()).isEqualTo(email);
		assertThat(result.getBio()).isEqualTo(null);
		assertThat(result.getImage()).isEqualTo(null);

		then(registerUserStatePort).should().registerUser(user);
		then(user).should().getEmail();

	}

	private User givenUserWithoutIdWillSucceed(RegisterUserCommand command) {
		User user = Mockito.mock(User.class);

		given(user.getUsername()).willReturn(command.getUsername());
		given(user.getEmail()).willReturn(command.getEmail());
		given(user.getBio()).willReturn(null);
		given(user.getImage()).willReturn(null);

		mockStatic(User.class);
		given(User.withoutId(
			eq(command.getUsername()),
			eq(command.getEmail()),
			any()
		)).willReturn(user);

		return user;
	}

}

package com.hani.realworld.profile.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.common.util.PasswordEncoderUtil;
import com.hani.realworld.profile.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.profile.application.port.in.result.UserResult;
import com.hani.realworld.profile.application.port.out.RegisterUserStatePort;
import com.hani.realworld.profile.domain.Password;
import com.hani.realworld.profile.domain.User;

class RegisterUserServiceTest {

	private final RegisterUserStatePort registerUserStatePort =
		Mockito.mock(RegisterUserStatePort.class);

	private final PasswordEncoder passwordEncoder =
		new BCryptPasswordEncoder();

	private final RegisterUserService registerUserService =
		new RegisterUserService(registerUserStatePort, passwordEncoder);

	@Test
	void registerUser_Succeeds() {
		// given
		final String username = "username";
		final String email = "user@email.com";
		final String password = "password";
		RegisterUserCommand command = new RegisterUserCommand(
			username, email, password);

		User user = givenUserWithoutIdWillSucceed(username, email, password);

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

	private User givenUserWithoutIdWillSucceed(
		String username,
		String email,
		String password) {
		User user = Mockito.mock(User.class);
		Password pass = new Password(password);

		given(user.getUsername()).willReturn(username);
		given(user.getEmail()).willReturn(email);
		given(user.getBio()).willReturn(null);
		given(user.getImage()).willReturn(null);

		MockedStatic<User> staticUser = mockStatic(User.class);
		given(User.withoutId(eq(username), eq(email), any()))
			.willReturn(user);

		PasswordEncoderUtil.encode(pass);
		return user;
	}

}

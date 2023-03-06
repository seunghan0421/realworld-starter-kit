package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.util.AssertUserContentUtil.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.infra.jwt.JwtProvider;
import com.hani.realworld.user.application.port.in.command.LoginUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;
import com.hani.realworld.user.application.port.out.LoadUserWithEmailPort;
import com.hani.realworld.user.domain.Password;
import com.hani.realworld.user.domain.User;

class LoginUserServiceTest {

	private final LoadUserWithEmailPort loadUserWithEmailPort =
		Mockito.mock(LoadUserWithEmailPort.class);

	private final JwtProvider jwtProvider =
		Mockito.mock(JwtProvider.class);

	private final PasswordEncoder passwordEncoder =
		new BCryptPasswordEncoder();

	private final LoginUserService loginUserService =
		new LoginUserService(loadUserWithEmailPort, jwtProvider, passwordEncoder);

	@Test
	void loginUser_Succeeds() {
		// given
		User user = givenLoadUserPortWillSucceeds();
		givenJwtProviderMakeTokenWillSucceeds();

		LoginUserCommand command = new LoginUserCommand(
			"user@email.com",
			"validPassword");

		// when
		LoginUserResult result = loginUserService.login(command);

		// then
		assertThat(result.getToken()).isNotBlank();
		assertUserContent(result);

		then(loadUserWithEmailPort).should().loadUser(eq("user@email.com"));
		then(jwtProvider).should().generate(eq("user@email.com"), eq("username"));
	}

	private User givenLoadUserPortWillSucceeds() {
		User user = Mockito.mock(User.class);
		Password password = new Password("validPassword");
		password.encode(passwordEncoder::encode);

		given(user.getUsername()).willReturn("username");
		given(user.getEmail()).willReturn("user@email.com");
		given(user.getBio()).willReturn("bio");
		given(user.getImage()).willReturn("https://image.jpeg");

		given(loadUserWithEmailPort.loadUser(eq("user@email.com")))
			.willReturn(user);

		return user;
	}

	private void givenJwtProviderMakeTokenWillSucceeds() {
		given(jwtProvider.generate(eq("user@email.com"), eq("username")))
			.willReturn("validToken");
	}

}

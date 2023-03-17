package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.UserFixture.*;
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
import com.hani.realworld.user.domain.User;

class LoginUserServiceTest {

	private final LoadUserWithEmailPort loadUserWithEmailPort =
		Mockito.mock(LoadUserWithEmailPort.class);

	private final JwtProvider jwtProvider =
		Mockito.mock(JwtProvider.class);

	private final LoginUserService loginUserService =
		new LoginUserService(loadUserWithEmailPort, jwtProvider, new BCryptPasswordEncoder());

	@Test
	void loginUser_Succeeds() {
		// given
		User user = getMockUSER1();
		final String token = "user1 valid token";

		LoginUserCommand command = new LoginUserCommand(USER1.getEmail(), "password1");

		given(loadUserWithEmailPort.loadUserWithEmail(eq(USER1.getEmail())))
			.willReturn(user);
		given(jwtProvider.generate(eq(USER1.getEmail())))
			.willReturn(token);

		// when
		LoginUserResult result = loginUserService.login(command);

		// then
		assertThat(result.getToken()).isEqualTo(token);
		assertThat(result.getEmail()).isEqualTo(USER1.getEmail());
		assertThat(result.getUsername()).isEqualTo(USER1.getUsername());
		assertThat(result.getBio()).isEqualTo(USER1.getBio());
		assertThat(result.getImage()).isEqualTo(USER1.getImage());

		then(loadUserWithEmailPort).should().loadUserWithEmail(eq(USER1.getEmail()));
		then(user).should().verifyPassword(any());
		then(jwtProvider).should().generate(eq(USER1.getEmail()));
	}

}

package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.common.fixture.UserServiceFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.user.application.port.in.result.UserResult;
import com.hani.realworld.user.application.port.out.LoadUserWithEmailPort;
import com.hani.realworld.user.application.port.out.RegisterProfileStatePort;
import com.hani.realworld.user.application.port.out.RegisterUserStatePort;
import com.hani.realworld.user.domain.User;

class RegisterUserServiceTest {

	private final RegisterUserStatePort registerUserStatePort =
		Mockito.mock(RegisterUserStatePort.class);

	private final RegisterProfileStatePort registerProfileStatePort =
		Mockito.mock(RegisterProfileStatePort.class);

	private final LoadUserWithEmailPort loadUserWithEmailPort =
		Mockito.mock(LoadUserWithEmailPort.class);

	private final PasswordEncoder passwordEncoder =
		new BCryptPasswordEncoder();

	private final RegisterUserService registerUserService =
		new RegisterUserService(registerUserStatePort,
			registerProfileStatePort,
			loadUserWithEmailPort,
			passwordEncoder);

	// TODO mock static test 해결해야함
	@Test
	void registerUser_Succeeds() {
		// given
		User user = givenAnUserWithUser1();

		RegisterUserCommand command = new RegisterUserCommand(
			USER1.getUsername(),
			USER1.getEmail(),
			"password1");

		MockedStatic<User> mockUserClass = mockStatic(User.class);
		mockUserClass.when(() -> User.withoutId(any(), any(), any()))
			.thenReturn(user);

		given(loadUserWithEmailPort.loadUserWithEmail(user.getEmail()))
			.willReturn(user);

		// when
		UserResult result = registerUserService.register(command);

		// then
		assertThat(result.getUsername()).isEqualTo(USER1.getUsername());
		assertThat(result.getEmail()).isEqualTo(USER1.getEmail());
		assertThat(result.getBio()).isEqualTo(USER1.getBio());
		assertThat(result.getImage()).isEqualTo(USER1.getImage());

		then(user).should().encodePassword(any());
		then(registerUserStatePort).should().register(any());
		then(registerProfileStatePort).should().register(any());

		mockUserClass.close();
	}

}

package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.infra.jwt.JwtProvider;
import com.hani.realworld.infra.jwt.LoginUser;
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

	private final JwtProvider jwtProvider =
		Mockito.mock(JwtProvider.class);

	private final UpdateUserService updateUserService =
		new UpdateUserService(
			loadUserWithIdPort,
			updateUserStatePort,
			new BCryptPasswordEncoder(),
			jwtProvider);

	@DisplayName("유저 정보 수정 서비스 테스트 - 성공")
	@Test
	void updateUserState_Succeeds(){
		// given
		User user1 = getMockUSER1();
		User updatedUser = defaultUser()
			.withUserId(USER1.getId())
			.withEmail(USER2.getEmail())
			.withUsername(USER2.getUsername())
			.withImage(USER2.getImage())
			.withBio(USER2.getBio())
			.build();

		UpdateUserCommand command = new UpdateUserCommand(
			USER2.getEmail(),
			USER2.getUsername(),
			null,
			USER2.getImage(),
			USER2.getBio());

		given(loadUserWithIdPort.loadUserWithId(eq(USER1.getId())))
			.willReturn(user1);
		given(jwtProvider.generate(eq(USER2.getEmail())))
			.willReturn("new jwt token");
		given(user1.update(
			eq(USER2.getEmail()),
			eq(USER2.getUsername()),
			any(),
			eq(USER2.getImage()),
			eq(USER2.getBio()), any()))
			.willReturn(updatedUser);

		// when
		LoginUserResult result = updateUserService.updateUser(command, USER1.getId().getValue());

		// then
		assertThat(result.getUsername()).isEqualTo(USER2.getUsername());
		assertThat(result.getEmail()).isEqualTo(USER2.getEmail());
		assertThat(result.getImage()).isEqualTo(USER2.getImage());
		assertThat(result.getBio()).isEqualTo(USER2.getBio());

		then(loadUserWithIdPort).should().loadUserWithId(eq(USER1.getId()));
		then(user1).should().update(
			eq(USER2.getEmail()),
			eq(USER2.getUsername()),
			any(),
			eq(USER2.getImage()),
			eq(USER2.getBio()), any());
		then(jwtProvider).should().generate(eq(USER2.getEmail()));
		then(updateUserStatePort).should().updateUserState(any());
	}

}

package com.hani.realworld.profile.application.service;

import static com.hani.realworld.profile.domain.User.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.profile.application.port.in.result.UserResult;
import com.hani.realworld.profile.application.port.out.LoadUserWithIdPort;
import com.hani.realworld.profile.domain.User;

class GetUserServiceTest {

	private final LoadUserWithIdPort loadUserWithIdPort =
		Mockito.mock(LoadUserWithIdPort.class);

	private final GetUserService getUserService =
		new GetUserService(loadUserWithIdPort);

	@Test
	void getUser_Succeeds() {
		// given
		UserId userId = new UserId(42L);
		User user = givenAnUserWithId(userId);

		// when
		UserResult result = getUserService.getUser(userId);

		// then
		assertThat(result.getUsername()).isEqualTo("username");
		assertThat(result.getEmail()).isEqualTo("user@naver.com");
		assertThat(result.getBio()).isEqualTo("bio");
		assertThat(result.getImage()).isEqualTo("http://image.jpeg");

		then(loadUserWithIdPort).should().loadUser(eq(userId));
		then(user).should().getEmail();
	}

	private User givenAnUserWithId(UserId userId) {
		User user = Mockito.mock(User.class);

		given(user.getUsername()).willReturn("username");
		given(user.getEmail()).willReturn("user@naver.com");
		given(user.getBio()).willReturn("bio");
		given(user.getImage()).willReturn("http://image.jpeg");

		given(loadUserWithIdPort.loadUser(eq(userId)))
			.willReturn(user);

		return user;
	}
}

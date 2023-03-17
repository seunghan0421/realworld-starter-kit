package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.user.application.port.in.result.UserResult;
import com.hani.realworld.user.application.port.out.LoadUserWithIdPort;
import com.hani.realworld.user.domain.User;

class GetUserServiceTest {

	private final LoadUserWithIdPort loadUserWithIdPort =
		Mockito.mock(LoadUserWithIdPort.class);

	private final GetUserService getUserService =
		new GetUserService(loadUserWithIdPort);

	@Test
	void getUser_Succeeds() {
		// given
		User user = USER1;

		given(loadUserWithIdPort.loadUserWithId(eq(USER1.getId())))
			.willReturn(user);

		// when
		UserResult result = getUserService.getUser(USER1.getId().getValue());

		// then
		assertThat(result.getUsername()).isEqualTo(USER1.getUsername());
		assertThat(result.getEmail()).isEqualTo(USER1.getEmail());
		assertThat(result.getBio()).isEqualTo(USER1.getBio());
		assertThat(result.getImage()).isEqualTo(USER1.getImage());

		then(loadUserWithIdPort).should().loadUserWithId(eq(USER1.getId()));
	}

}

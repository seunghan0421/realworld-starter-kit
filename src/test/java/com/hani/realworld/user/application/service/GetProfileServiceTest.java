package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.user.domain.User.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsernamePort;
import com.hani.realworld.user.domain.Profile;

class GetProfileServiceTest {

	private final LoadProfileWithUsernamePort loadProfileWithUsernamePort =
		Mockito.mock(LoadProfileWithUsernamePort.class);

	private final LoadProfileWithUserIdPort loadProfileWithUserIdPort =
		Mockito.mock(LoadProfileWithUserIdPort.class);

	private final GetProfileService getProfileService =
		new GetProfileService(loadProfileWithUsernamePort, loadProfileWithUserIdPort);

	// USER1이 USER2를 조회하는 상황
	// 로그인 O, follow X
	@DisplayName("프로필 조회 서비스 테스트 - 성공, 로그인 되어있는 상태")
	@Test
	void getProfile_withLogin_Succeeds() {
		// given
		Profile base = getMockPROFILE1();
		Profile target = PROFILE2;

		String targetUsername = target.getUser().getUsername();
		Optional<Long> baseUserId = Optional.of(base.getUser().getId().getValue());

		given(loadProfileWithUsernamePort.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);
		given(loadProfileWithUserIdPort.loadProfileWithUserId(eq(new UserId(baseUserId.get()))))
			.willReturn(base);

		// when
		ProfileResult result = getProfileService.getProfile(targetUsername, baseUserId);

		// then
		assertThat(result.getUsername()).isEqualTo(USER2.getUsername());
		assertThat(result.getBio()).isEqualTo(USER2.getBio());
		assertThat(result.getImage()).isEqualTo(USER2.getImage());
		assertThat(result.isFollowing()).isTrue();

		then(loadProfileWithUserIdPort).should().loadProfileWithUserId(eq(USER1.getId()));
		then(loadProfileWithUsernamePort).should().loadProfileWithUsername(eq(USER2.getUsername()));
		then(base).should().isFollowing(USER2);
	}

	// USER1이 USER2를 조회하는 상황
	// 로그인 X, follow X
	@DisplayName("프로필 조회 서비스 테스트 - 성공, 로그인 되어있지 않은 상태")
	@Test
	void getProfile_withOutLogin_Succeeds() {
		// given
		Profile target = PROFILE2;

		String targetUsername = target.getUser().getUsername();
		Optional<Long> baseUserId = Optional.empty();

		given(loadProfileWithUsernamePort.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);

		// when
		ProfileResult result = getProfileService.getProfile(targetUsername, baseUserId);

		// then
		assertThat(result.getUsername()).isEqualTo(USER2.getUsername());
		assertThat(result.getBio()).isEqualTo(USER2.getBio());
		assertThat(result.getImage()).isEqualTo(USER2.getImage());
		assertThat(result.isFollowing()).isFalse();

		then(loadProfileWithUsernamePort).should().loadProfileWithUsername(eq(USER2.getUsername()));
	}


}

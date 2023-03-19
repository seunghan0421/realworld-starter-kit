package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.user.domain.User.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsernamePort;
import com.hani.realworld.user.application.port.out.UpdateProfileStatePort;
import com.hani.realworld.user.domain.Profile;

class FollowProfileServiceTest {

	private final LoadProfileWithUsernamePort loadProfileWithUsernamePort =
		Mockito.mock(LoadProfileWithUsernamePort.class);

	private final LoadProfileWithUserIdPort loadProfileWithUserIdPort =
		Mockito.mock(LoadProfileWithUserIdPort.class);

	private final UpdateProfileStatePort updateProfileStatePort =
		Mockito.mock(UpdateProfileStatePort.class);

	private final FollowProfileService followProfileService =
		new FollowProfileService(loadProfileWithUsernamePort, loadProfileWithUserIdPort, updateProfileStatePort);

	// USER1가 USER2 follow - 성공
	@DisplayName("팔로우 서비스 테스트 - 성공")
	@Test
	void followProfile_Succeeds() {
		// given
		Profile base = getMockPROFILE1();
		Profile target = PROFILE2;

		String targetUsername = target.getUser().getUsername();
		Long baseUserId = base.getUser().getId().getValue();

		given(loadProfileWithUsernamePort.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);
		given(loadProfileWithUserIdPort.loadProfileWithUserId(eq(new UserId(baseUserId))))
			.willReturn(base);
		given(base.isFollowing(target.getUser()))
			.willReturn(true);

		// when
		ProfileResult result = followProfileService.followProfile(targetUsername, baseUserId);

		// then
		assertThat(result.getUsername()).isEqualTo(USER2.getUsername());
		assertThat(result.getBio()).isEqualTo(USER2.getBio());
		assertThat(result.getImage()).isEqualTo(USER2.getImage());
		assertThat(result.isFollowing()).isTrue();

		then(loadProfileWithUserIdPort).should().loadProfileWithUserId(eq(USER1.getId()));
		then(loadProfileWithUsernamePort).should().loadProfileWithUsername(eq(USER2.getUsername()));
		then(updateProfileStatePort).should().updateProfile(eq(base));
		then(base).should().follow(eq(USER2));
		then(base).should().isFollowing(eq(USER2));
	}

	// USER1가 USER2 follow - 실패 : 이미 팔로우 되어 있음
	@DisplayName("팔로우 서비스 - 실패 , 이미 팔로우 되어 있음")
	@Test
	void followProfile_Failure() {
		// given
		Profile base = getMockPROFILE1();
		Profile target = PROFILE2;

		String targetUsername = target.getUser().getUsername();
		Long baseUserId = base.getUser().getId().getValue();

		given(loadProfileWithUsernamePort.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);
		given(loadProfileWithUserIdPort.loadProfileWithUserId(eq(new UserId(baseUserId))))
			.willReturn(base);

		doThrow(new IllegalStateException()).when(base).follow(eq(USER2));

		// when
		assertThatThrownBy(() -> followProfileService.followProfile(targetUsername, baseUserId))
			.isInstanceOf(IllegalStateException.class);

		// then
		then(loadProfileWithUserIdPort).should().loadProfileWithUserId(eq(USER1.getId()));
		then(loadProfileWithUsernamePort).should().loadProfileWithUsername(eq(USER2.getUsername()));
		then(base).should().follow(eq(USER2));
	}

}

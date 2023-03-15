package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsernamePort;
import com.hani.realworld.user.application.port.out.UpdateProfileStatePort;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

class UnFollowProfileServiceTest {

	private final LoadProfileWithUsernamePort loadProfileWithUsernamePort =
		Mockito.mock(LoadProfileWithUsernamePort.class);

	private final LoadProfileWithUserIdPort loadProfileWithUserIdPort =
		Mockito.mock(LoadProfileWithUserIdPort.class);

	private final UpdateProfileStatePort updateProfileStatePort =
		Mockito.mock(UpdateProfileStatePort.class);

	private final UnFollowProfileService unFollowProfileService =
		new UnFollowProfileService(loadProfileWithUsernamePort, loadProfileWithUserIdPort, updateProfileStatePort);

	// USER1가 USER2 unfollow - 성공
	@Test
	void unfollowProfile_Succeeds() {
		// given
		Profile target = getMockPROFILE2();
		Profile base = getMockPROFILE1();

		String targetUsername = target.getUser().getUsername();
		Long baseUserId = base.getUser().getId().getValue();

		given(loadProfileWithUsernamePort.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);
		given(loadProfileWithUserIdPort.loadProfileWithUserId(eq(new User.UserId(baseUserId))))
			.willReturn(base);
		given(base.isFollowing(target.getUser()))
			.willReturn(false);

		// when
		ProfileResult result = unFollowProfileService.unfollowProfile(targetUsername, baseUserId);

		// then
		assertThat(result.getUsername()).isEqualTo(USER2.getUsername());
		assertThat(result.getBio()).isEqualTo(USER2.getBio());
		assertThat(result.getImage()).isEqualTo(USER2.getImage());
		assertThat(result.isFollowing()).isFalse();

		then(loadProfileWithUserIdPort).should().loadProfileWithUserId(eq(USER1.getId()));
		then(loadProfileWithUsernamePort).should().loadProfileWithUsername(eq(USER2.getUsername()));
		then(updateProfileStatePort).should().updateProfile(eq(base));
		then(base).should().unfollow(eq(USER2));
		then(base).should().isFollowing(eq(USER2));
	}

	// USER1가 USER2 unfollow - 실패 : 이미 팔로우 되어 않음
	@Test
	void unfollowProfile_Failure() {
		// given
		Profile target = getMockPROFILE2();
		Profile base = getMockPROFILE1();

		String targetUsername = target.getUser().getUsername();
		Long baseUserId = base.getUser().getId().getValue();

		given(loadProfileWithUsernamePort.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);
		given(loadProfileWithUserIdPort.loadProfileWithUserId(eq(new User.UserId(baseUserId))))
			.willReturn(base);
		doThrow(new IllegalStateException()).when(base).unfollow(eq(USER2));

		// when
		assertThatThrownBy(() -> unFollowProfileService.unfollowProfile(targetUsername, baseUserId))
			.isInstanceOf(IllegalStateException.class);

		// then
		then(loadProfileWithUserIdPort).should().loadProfileWithUserId(eq(USER1.getId()));
		then(loadProfileWithUsernamePort).should().loadProfileWithUsername(eq(USER2.getUsername()));
		then(base).should().unfollow(eq(USER2));
	}

}

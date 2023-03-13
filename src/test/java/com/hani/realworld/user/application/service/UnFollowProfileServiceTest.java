package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.ProfileServiceFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserId;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsername;
import com.hani.realworld.user.application.port.out.UpdateProfileStatePort;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

class UnFollowProfileServiceTest {

	private final LoadProfileWithUsername loadProfileWithUsername =
		Mockito.mock(LoadProfileWithUsername.class);

	private final LoadProfileWithUserId loadProfileWithUserId =
		Mockito.mock(LoadProfileWithUserId.class);

	private final UpdateProfileStatePort updateProfileStatePort =
		Mockito.mock(UpdateProfileStatePort.class);

	private final UnFollowProfileService unFollowProfileService =
		new UnFollowProfileService(loadProfileWithUsername, loadProfileWithUserId, updateProfileStatePort);

	// USER1가 USER2 unfollow - 성공
	@Test
	void unfollowProfile_Succeeds() {
		// given
		Profile target = givenAnProfileOfUser2();
		Profile base = givenAnProfileOfUser1();

		String targetUsername = target.getUser().getUsername();
		Long baseUserId = base.getUser().getId().getValue();

		given(loadProfileWithUsername.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);
		given(loadProfileWithUserId.loadProfileWithUserId(eq(new User.UserId(baseUserId))))
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

		then(loadProfileWithUserId).should().loadProfileWithUserId(eq(USER1.getId()));
		then(loadProfileWithUsername).should().loadProfileWithUsername(eq(USER2.getUsername()));
		then(updateProfileStatePort).should().updateProfile(eq(base));
		then(base).should().unfollow(eq(USER2));
		then(base).should().isFollowing(eq(USER2));
	}

	// USER1가 USER2 unfollow - 실패 : 이미 팔로우 되어 않음
	@Test
	void unfollowProfile_Failure() {
		// given
		Profile target = givenAnProfileOfUser2();
		Profile base = givenAnProfileOfUser1();

		String targetUsername = target.getUser().getUsername();
		Long baseUserId = base.getUser().getId().getValue();

		given(loadProfileWithUsername.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);
		given(loadProfileWithUserId.loadProfileWithUserId(eq(new User.UserId(baseUserId))))
			.willReturn(base);
		doThrow(new IllegalStateException()).when(base).unfollow(eq(USER2));

		// when
		assertThatThrownBy(() -> unFollowProfileService.unfollowProfile(targetUsername, baseUserId))
			.isInstanceOf(IllegalStateException.class);

		// then
		then(loadProfileWithUserId).should().loadProfileWithUserId(eq(USER1.getId()));
		then(loadProfileWithUsername).should().loadProfileWithUsername(eq(USER2.getUsername()));
		then(base).should().unfollow(eq(USER2));
	}

}

package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.ProfileServiceFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.user.domain.User.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserId;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsername;
import com.hani.realworld.user.domain.Profile;

class FollowProfileServiceTest {

	private final LoadProfileWithUsername loadProfileWithUsername =
		Mockito.mock(LoadProfileWithUsername.class);

	private final LoadProfileWithUserId loadProfileWithUserId =
		Mockito.mock(LoadProfileWithUserId.class);

	private final FollowProfileService followProfileService =
		new FollowProfileService(loadProfileWithUsername, loadProfileWithUserId);

	// USER1가 USER2 follow - 성공
	@Test
	void followProfile_Succeeds() {
		// given
		Profile target = givenAnProfileOfUser2();
		Profile base = givenAnProfileOfUser1();

		String targetUsername = target.getUser().getUsername();
		Long baseUserId = base.getUser().getId().getValue();

		given(loadProfileWithUsername.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);
		given(loadProfileWithUserId.loadProfileWithUserId(eq(new UserId(baseUserId))))
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

		then(loadProfileWithUserId).should().loadProfileWithUserId(eq(USER1.getId()));
		then(loadProfileWithUsername).should().loadProfileWithUsername(eq(USER2.getUsername()));
		then(base).should().follow(eq(USER2));
		then(base).should().isFollowing(eq(USER2));
	}

	// USER1가 USER2 follow - 실패 : 이미 팔로우 되어 있음
	@Test
	void followProfile_Failure() {
		// given
		Profile target = givenAnProfileOfUser2();
		Profile base = givenAnProfileOfUser1();

		String targetUsername = target.getUser().getUsername();
		Long baseUserId = base.getUser().getId().getValue();

		given(loadProfileWithUsername.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);
		given(loadProfileWithUserId.loadProfileWithUserId(eq(new UserId(baseUserId))))
			.willReturn(base);
		// TODO: 여기 왜 eq(target.getUser()) 안되는지 확인해야함
		doThrow(new IllegalStateException()).when(base).follow(eq(USER2));

		// when
		assertThatThrownBy(() -> followProfileService.followProfile(targetUsername, baseUserId))
			.isInstanceOf(IllegalStateException.class);

		// then
		then(loadProfileWithUserId).should().loadProfileWithUserId(eq(USER1.getId()));
		then(loadProfileWithUsername).should().loadProfileWithUsername(eq(USER2.getUsername()));
		then(base).should().follow(eq(USER2));
	}

}

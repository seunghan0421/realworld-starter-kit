package com.hani.realworld.user.application.service;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.user.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.in.result.UserResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserId;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsername;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

class GetProfileServiceTest {

	private final LoadProfileWithUsername loadProfileWithUsername =
		Mockito.mock(LoadProfileWithUsername.class);

	private final LoadProfileWithUserId loadProfileWithUserId =
		Mockito.mock(LoadProfileWithUserId.class);

	private final GetProfileService getProfileService =
		new GetProfileService(loadProfileWithUsername, loadProfileWithUserId);

	// USER1이 USER2를 조회하는 상황
	// 로그인 O, follow X
	@Test
	void getProfile_withLogin_Succeeds() {
		// given
		Profile target = givenAnProfileOfUser2();
		Profile base = givenAnProfileOfUser1();

		String targetUsername = target.getUser().getUsername();
		Optional<Long> baseUserId = Optional.of(base.getUser().getId().getValue());

		given(loadProfileWithUsername.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);
		given(loadProfileWithUserId.loadProfileWithUserId(eq(USER1.getId())))
			.willReturn(base);

		// when
		ProfileResult result = getProfileService.getProfile(targetUsername, baseUserId);

		// then
		assertThat(result.getUsername()).isEqualTo(USER2.getUsername());
		assertThat(result.getBio()).isEqualTo(USER2.getBio());
		assertThat(result.getImage()).isEqualTo(USER2.getImage());
		assertThat(result.isFollowing()).isTrue();

		then(loadProfileWithUserId).should().loadProfileWithUserId(eq(USER1.getId()));
		then(loadProfileWithUsername).should().loadProfileWithUsername(eq(USER2.getUsername()));
		then(base).should().isFollowing(USER2);
	}

	// USER1이 USER2를 조회하는 상황
	// 로그인 X, follow X
	@Test
	void getProfile_withOutLogin_Succeeds() {
		// given
		Profile target = givenAnProfileOfUser2();

		String targetUsername = target.getUser().getUsername();
		Optional<Long> baseUserId = Optional.empty();

		given(loadProfileWithUsername.loadProfileWithUsername(eq(targetUsername)))
			.willReturn(target);

		// when
		ProfileResult result = getProfileService.getProfile(targetUsername, baseUserId);

		// then
		assertThat(result.getUsername()).isEqualTo(USER2.getUsername());
		assertThat(result.getBio()).isEqualTo(USER2.getBio());
		assertThat(result.getImage()).isEqualTo(USER2.getImage());
		assertThat(result.isFollowing()).isFalse();

		then(loadProfileWithUsername).should().loadProfileWithUsername(eq(USER2.getUsername()));
	}

	private Profile givenAnProfileOfUser1() {
		Profile profile = Mockito.mock(Profile.class);

		given(profile.getUser()).willReturn(USER1);
		given(profile.getId()).willReturn(PROFILE1.getId());
		given(profile.getFollowees()).willReturn(PROFILE1.getFollowees());

		given(profile.isFollowing(eq(USER2)))
			.willReturn(true);

		return profile;
	}

	private Profile givenAnProfileOfUser2() {
		Profile profile = Mockito.mock(Profile.class);

		given(profile.getUser()).willReturn(USER2);
		given(profile.getId()).willReturn(PROFILE2.getId());
		given(profile.getFollowees()).willReturn(PROFILE2.getFollowees());

		return profile;
	}

}

package com.hani.realworld.common.fixture;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;

import org.mockito.Mockito;

import com.hani.realworld.user.domain.Profile;

public class ProfileServiceFixture {

	public static Profile givenAnProfileOfUser1() {
		Profile profile = Mockito.mock(Profile.class);

		given(profile.getUser()).willReturn(USER1);
		given(profile.getId()).willReturn(PROFILE1.getId());
		given(profile.getFollowees()).willReturn(PROFILE1.getFollowees());

		given(profile.isFollowing(eq(USER2)))
			.willReturn(true);

		return profile;
	}

	public static Profile givenAnProfileOfUser2() {
		Profile profile = Mockito.mock(Profile.class);

		given(profile.getUser()).willReturn(USER2);
		given(profile.getId()).willReturn(PROFILE2.getId());
		given(profile.getFollowees()).willReturn(PROFILE2.getFollowees());

		given(profile.isFollowing(eq(USER2)))
			.willReturn(false);

		return profile;
	}
}

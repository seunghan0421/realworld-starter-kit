package com.hani.realworld.profile.domain;

import static com.hani.realworld.profile.domain.User.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.hani.realworld.common.data.ProfileTestData;
import com.hani.realworld.common.data.UserTestData;

class ProfileTest {

	@Test
	void follow_Succeeds() {
		// given
		Profile profile = ProfileTestData.defaultProfile().build();
		User user = UserTestData.defaultUser()
			.withUserId(new UserId(45L)).build();

		// when
		profile.follow(user);

		// then
		assertThat(profile.getFollowees().getFollowees())
			.hasSize(3)
			.contains(new UserId(45L));
	}

	@Test
	void follow_Failure_alreadyFollowed() {
		// given
		Profile profile = ProfileTestData.defaultProfile()
			.withFollowees(new Followees(
				UserTestData.defaultUser()
				.withUserId(new UserId(33L)).build().getId()))
			.build();
		User alreadyFollowedUser = UserTestData.defaultUser()
			.withUserId(new UserId(33L)).build();

		// when
		assertThrows(IllegalStateException.class, () ->  profile.follow(alreadyFollowedUser));
	}

	@Test
	void unfollow_Succeeds() {
		// given
		Profile profile = ProfileTestData.defaultProfile()
			.withFollowees(new Followees(
				UserTestData.defaultUser()
					.withUserId(new UserId(33L)).build().getId()))
			.build();
		User user = UserTestData.defaultUser()
			.withUserId(new UserId(33L)).build();

		// when
		profile.unfollow(user);

		// then
		assertThat(profile.getFollowees().getFollowees()).hasSize(0);
	}

	@Test
	void unfollow_Failure_NotFollowedUser() {
		// given
		Profile profile = ProfileTestData.defaultProfile().build();
		User notFollowedUser = UserTestData.defaultUser()
			.withUserId(new UserId(133L)).build();

		// when
		assertThrows(IllegalStateException.class, () ->  profile.unfollow(notFollowedUser));
	}

	@Test
	void isFollowing_Succeeds() {
		// given
		Profile profile = ProfileTestData.defaultProfile()
			.withFollowees(new Followees(
				UserTestData.defaultUser()
					.withUserId(new UserId(33L)).build().getId()))
			.build();
		User user = UserTestData.defaultUser()
			.withUserId(new UserId(33L)).build();

		// when
		boolean result = profile.isFollowing(user);

		// then
		assertThat(result).isTrue();
	}
}

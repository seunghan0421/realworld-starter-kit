package com.hani.realworld.user.domain;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.user.domain.User.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hani.realworld.common.fixture.ProfileFixture;
import com.hani.realworld.common.fixture.UserFixture;

class ProfileTest {

	@Test
	void profile_withoutId_test() {
		assertDoesNotThrow(() -> Profile.withoutId(USER1));
	}

	@Test
	void profile_withId_test() {
		assertDoesNotThrow(() -> Profile.withId(
			PROFILE1.getId(),
			PROFILE1.getUser(),
			PROFILE1.getFollowees()));
	}

	@Test
	void follow_Succeeds() {
		// given
		Profile profile = ProfileFixture.defaultProfile().build();
		User user = UserFixture.defaultUser()
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
		Profile profile = ProfileFixture.defaultProfile()
			.withFollowees(new Followees(
				UserFixture.defaultUser()
				.withUserId(new UserId(33L)).build().getId()))
			.build();
		User alreadyFollowedUser = UserFixture.defaultUser()
			.withUserId(new UserId(33L)).build();

		// when
		assertThrows(IllegalStateException.class, () ->  profile.follow(alreadyFollowedUser));
	}

	@Test
	void unfollow_Succeeds() {
		// given
		Profile profile = ProfileFixture.defaultProfile()
			.withFollowees(new Followees(
				UserFixture.defaultUser()
					.withUserId(new UserId(33L)).build().getId()))
			.build();
		User user = UserFixture.defaultUser()
			.withUserId(new UserId(33L)).build();

		// when
		profile.unfollow(user);

		// then
		assertThat(profile.getFollowees().getFollowees()).hasSize(0);
	}

	@Test
	void unfollow_Failure_NotFollowedUser() {
		// given
		Profile profile = ProfileFixture.defaultProfile().build();
		User notFollowedUser = UserFixture.defaultUser()
			.withUserId(new UserId(133L)).build();

		// when
		assertThrows(IllegalStateException.class, () ->  profile.unfollow(notFollowedUser));
	}

	@Test
	void isFollowing_Succeeds() {
		// given
		Profile profile = ProfileFixture.defaultProfile()
			.withFollowees(new Followees(
				UserFixture.defaultUser()
					.withUserId(new UserId(33L)).build().getId()))
			.build();
		User user = UserFixture.defaultUser()
			.withUserId(new UserId(33L)).build();

		// when
		boolean result = profile.isFollowing(user);

		// then
		assertThat(result).isTrue();
	}
}

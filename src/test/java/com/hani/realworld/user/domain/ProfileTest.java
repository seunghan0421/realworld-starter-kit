package com.hani.realworld.user.domain;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.user.domain.User.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hani.realworld.common.fixture.ProfileFixture;
import com.hani.realworld.common.fixture.UserFixture;

class ProfileTest {

	@DisplayName("프로필 팩토리 도메인 테스트 - 성공, Profile.without()")
	@Test
	void profile_withoutId_test() {
		assertDoesNotThrow(() -> Profile.withoutId(USER1));
	}

	@DisplayName("프로필 팩토리 도메인 테스트 - 성공, Profile.with()")
	@Test
	void profile_withId_test() {
		assertDoesNotThrow(() -> Profile.withId(
			PROFILE1.getId(),
			PROFILE1.getUser(),
			PROFILE1.getFollowees()));
	}

	@DisplayName("프로필 팔로우 도메인 테스트 - 성공, profile.follow()")
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

	@DisplayName("프로필 팔로우 도메인 테스트 - 실패, 이미 팔로우 되어있음, profile.follow(), throw IllegalStateException")
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
		assertThrows(IllegalStateException.class, () -> profile.follow(alreadyFollowedUser));
	}

	@DisplayName("프로필 언팔로우 도메인 테스트 - 성공, profile.unfollow()")
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

	@DisplayName("프로필 언팔로우 도메인 테스트 - 실패, 팔로우 되어있지 않음, profile.unfollow(), throw IllegalStateException")
	@Test
	void unfollow_Failure_NotFollowedUser() {
		// given
		Profile profile = ProfileFixture.defaultProfile().build();
		User notFollowedUser = UserFixture.defaultUser()
			.withUserId(new UserId(133L)).build();

		// when
		assertThrows(IllegalStateException.class, () -> profile.unfollow(notFollowedUser));
	}

	@DisplayName("프로필 팔로우 여부 확인 도메인 테스트 - 성공, profile.isFollowing()")
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

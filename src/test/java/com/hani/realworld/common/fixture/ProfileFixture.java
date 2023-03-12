package com.hani.realworld.common.fixture;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.user.domain.Profile.*;
import static com.hani.realworld.user.domain.User.*;

import com.hani.realworld.user.domain.Followees;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

public class ProfileFixture {

	public static ProfileBuilder defaultProfile() {
		return new ProfileBuilder()
			.withProfileId(new ProfileId(33L))
			.withUser(UserFixture.defaultUser().build())
			.withFollowees(new Followees(
				UserFixture.defaultUser()
					.withUserId(new UserId(21L))
					.withUsername("user21").build().getId(),
				UserFixture.defaultUser()
					.withUserId(new UserId(22L))
					.withUsername("user22").build().getId()));
	}

	public static class ProfileBuilder {
		private ProfileId profileId;
		private User user;
		private Followees followees;

		public ProfileBuilder withProfileId(ProfileId profileId) {
			this.profileId = profileId;
			return this;
		}

		public ProfileBuilder withUser(User user) {
			this.user = user;
			return this;
		}

		public ProfileBuilder withFollowees(Followees followees) {
			this.followees = followees;
			return this;
		}

		public Profile build() {
			return Profile.withId(
				this.profileId,
				this.user,
				this.followees);
		}
	}

	public static final Profile PROFILE1 = defaultProfile()
		.withProfileId(new ProfileId(1L))
		.withUser(USER1)
		.withFollowees(new Followees(USER2.getId()))
		.build();

	public static final Profile PROFILE2 = defaultProfile()
		.withProfileId(new ProfileId(2L))
		.withUser(USER2)
		.withFollowees(null)
		.build();

	// public static ProfileResponse getResponseFromProfile(Profile profile) {
	// 	return ProfileResponse.of(new ProfileResult(
	// 		profile.getUser().getUsername(),
	// 		profile.getUser().getBio(),
	// 		profile.getUser().getImage(),
	// 		true));
	// }
}

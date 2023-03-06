package com.hani.realworld.common.data;

import static com.hani.realworld.user.domain.Profile.*;
import static com.hani.realworld.user.domain.User.*;

import com.hani.realworld.user.domain.Followees;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

public class ProfileTestData {

	public static ProfileBuilder defaultProfile() {
		return new ProfileBuilder()
			.withProfileId(new ProfileId(33L))
			.withUser(UserTestData.defaultUser().build())
			.withFollowees(new Followees(
				UserTestData.defaultUser()
					.withUserId(new UserId(21L))
					.withUsername("user21").build().getId(),
				UserTestData.defaultUser()
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
}

package com.hani.realworld.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Profile {

	/* Profile Identification number */
	private final ProfileId id;

	/* User who has this profile*/
	private final User user;

	/* Users who followed the profile. */
	private final Followees followees;

	/**
	 * Creates an {@link Profile} entity without an ID. Use to create a new entity that is not yet
	 * persisted.
	 */
	public static Profile withoutId(User user) {
		return new Profile(null, user, new Followees());
	}

	/**
	 * Creates an {@link Profile} entity with an ID. Use to reconstitute a persisted entity.
	 */
	public static Profile withId(
		ProfileId profileId,
		User user,
		Followees followees) {
		return new Profile(profileId, user, followees);
	}

	/**
	 * The method to follow this profile.
	 */
	public void follow(User user) {
		this.followees.follow(user);
	}

	/**
	 * The method to unfollow this profile.
	 */
	public void unfollow(User user) {
		this.followees.unfollow(user);
	}

	/**
	 * check whether user follow this profile
	 */
	public boolean isFollowing(User user) {
		return this.followees.isFollow(user);
	}

	@Value
	public static class ProfileId {
		private Long value;
	}

}

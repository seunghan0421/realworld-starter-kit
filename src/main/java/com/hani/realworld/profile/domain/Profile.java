package com.hani.realworld.profile.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Profile {

	/* 프로필 식별번호 */
	private final ProfileId id;

	/* 프로필 사용자*/
	private final User user;

	/* 팔로위들, Users who followed the profile. */
	private final Followees followees;

	/**
	 * Creates an {@link Profile} entity without an ID. Use to create a new entity that is not yet
	 * persisted.
	 */
	public static Profile withoutId(
		User user,
		Followees followees) {
		return new Profile(null, user, followees);
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
	public void isFollowing(User user) {
		this.followees.isFollow(user);
	}

	@Value
	public static class ProfileId {
		private Long value;
	}

}

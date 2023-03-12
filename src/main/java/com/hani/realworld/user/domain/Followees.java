package com.hani.realworld.user.domain;

import static com.hani.realworld.common.util.PreConditions.*;
import static com.hani.realworld.user.domain.User.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.NonNull;

public class Followees {

	/**
	 * Those who followed the profile.
	 */
	private Set<UserId> users;

	public Followees(@NonNull Set<UserId> followees) {
		this.users = followees;
	}

	public Followees(@NonNull UserId... followees) {
		this.users = new HashSet<>(Arrays.asList(followees));
	}

	public Set<UserId> getFollowees() {
		return Collections.unmodifiableSet(this.users);
	}

	/**
	 * The method to follow the profile.
	 * Put the user id in the followee list.
	 *
	 * @throws IllegalStateException if user already follow the profile
	 */
	public void follow(User user) {
		checkState(!isFollow(user), "이미 팔로우한 프로필입니다.");

		this.users.add(user.getId());
	}

	/**
	 * The method to unfollow the profile.
	 * Remove the user id in the followee list.
	 *
	 * @throws IllegalStateException if user already follow the profile
	 */
	public void unfollow(User user) {
		checkState(isFollow(user), "팔로우하지 않은 프로필입니다.");

		this.users.remove(user.getId());
	}

	/**
	 * Follow check method
	 *
	 * @return boolean value whether user is following profile
	 */
	public boolean isFollow(User user) {
		return this.users.parallelStream()
			.anyMatch(id -> id.equals(user.getId()));
	}
}

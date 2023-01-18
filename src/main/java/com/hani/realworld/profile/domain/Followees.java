package com.hani.realworld.profile.domain;

import static com.hani.realworld.common.util.PreConditions.*;
import static com.hani.realworld.profile.domain.User.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.NonNull;

public class Followees {

	/**
	 * Those who followed the profile.
	 */
	private List<UserId> users;

	public Followees(@NonNull List<UserId> followees) {
		this.users = followees;
	}

	public Followees(@NonNull UserId... followees) {
		this.users = new ArrayList<>(Arrays.asList(followees));
	}

	public List<UserId> getFollowees() {
		return Collections.unmodifiableList(this.users);
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

		this.users.remove(user);
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

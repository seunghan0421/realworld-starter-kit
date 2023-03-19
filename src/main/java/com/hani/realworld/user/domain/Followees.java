package com.hani.realworld.user.domain;

import static com.hani.realworld.user.domain.User.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.hani.realworld.common.exception.user.AlreadyFollowProfileException;
import com.hani.realworld.common.exception.user.NotFollowedProfileException;

import lombok.NonNull;

public class Followees {

	/**
	 * Those who followed the profile.
	 */
	private Set<UserId> users;

	public Followees() {
		this.users = new HashSet<>();
	}

	public Followees(@NonNull Set<UserId> followees) {
		this.users = new HashSet<>(followees);
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
		if (isFollow(user))
			throw new AlreadyFollowProfileException();

		this.users.add(user.getId());
	}

	/**
	 * The method to unfollow the profile.
	 * Remove the user id in the followee list.
	 *
	 * @throws IllegalStateException if user did not follow the profile
	 */
	public void unfollow(User user) {
		if (!isFollow(user))
			throw new NotFollowedProfileException();

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

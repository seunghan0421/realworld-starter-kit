package com.hani.realworld.user.application.service;

import static com.hani.realworld.user.domain.User.*;

import java.util.Optional;

import com.hani.realworld.common.annotation.Query;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserId;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsername;
import com.hani.realworld.user.domain.Profile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetProfileService implements GetProfileQuery {

	private final LoadProfileWithUsername loadProfileWithUsername;
	private final LoadProfileWithUserId loadProfileWithUserId;

	@Override
	public ProfileResult getProfile(String username, Optional<Long> userId) {
		Profile target = loadProfileWithUsername.loadProfileWithUsername(username);
		// boolean isFollowing = false;

		// if (userId.isPresent()) {
		// 	Profile base = loadProfileWithUserId.loadProfileWithUserId(new UserId(userId.get()));
		//
		// 	isFollowing = base.isFollowing(target.getUser());
		// }

		boolean isFollowing = userId.map(id -> {
			Profile base = loadProfileWithUserId.loadProfileWithUserId(new UserId(id));

			return base.isFollowing(target.getUser());
		}).orElseGet(() -> false);

		return ProfileResult.of(target, isFollowing);
	}
}

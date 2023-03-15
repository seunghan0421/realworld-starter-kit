package com.hani.realworld.user.application.service;

import static com.hani.realworld.user.domain.User.*;

import java.util.Optional;

import com.hani.realworld.common.annotation.Query;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsernamePort;
import com.hani.realworld.user.domain.Profile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetProfileService implements GetProfileQuery {

	private final LoadProfileWithUsernamePort loadProfileWithUsernamePort;
	private final LoadProfileWithUserIdPort loadProfileWithUserIdPort;

	@Override
	public ProfileResult getProfile(String username, Optional<Long> userId) {
		Profile target = loadProfileWithUsernamePort.loadProfileWithUsername(username);

		boolean isFollowing = userId.map(id -> {
			Profile base = loadProfileWithUserIdPort.loadProfileWithUserId(new UserId(id));

			return base.isFollowing(target.getUser());
		}).orElseGet(() -> false);

		return ProfileResult.of(target, isFollowing);
	}
}

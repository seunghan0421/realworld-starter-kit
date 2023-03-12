package com.hani.realworld.user.application.service;

import static com.hani.realworld.user.domain.User.*;

import javax.transaction.Transactional;

import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.user.application.port.in.FollowProfileUseCase;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserId;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsername;
import com.hani.realworld.user.domain.Profile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@UseCase
public class FollowProfileService implements FollowProfileUseCase {

	private final LoadProfileWithUsername loadProfileWithUsername;
	private final LoadProfileWithUserId loadProfileWithUserId;

	@Override
	public ProfileResult followProfile(String username, Long userId) {
		Profile target = loadProfileWithUsername.loadProfileWithUsername(username);
		Profile base = loadProfileWithUserId.loadProfileWithUserId(new UserId(userId));

		base.follow(target.getUser());

		return ProfileResult.of(target, base.isFollowing(target.getUser()));
	}
}

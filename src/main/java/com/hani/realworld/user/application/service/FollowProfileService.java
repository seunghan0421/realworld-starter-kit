package com.hani.realworld.user.application.service;

import static com.hani.realworld.user.domain.User.*;

import javax.transaction.Transactional;

import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.user.application.port.in.FollowProfileUseCase;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsernamePort;
import com.hani.realworld.user.application.port.out.UpdateProfileStatePort;
import com.hani.realworld.user.domain.Profile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@UseCase
public class FollowProfileService implements FollowProfileUseCase {

	private final LoadProfileWithUsernamePort loadProfileWithUsernamePort;
	private final LoadProfileWithUserIdPort loadProfileWithUserIdPort;
	private final UpdateProfileStatePort updateProfileStatePort;

	@Override
	public ProfileResult followProfile(String username, Long userId) {
		Profile target = loadProfileWithUsernamePort.loadProfileWithUsername(username);
		Profile base = loadProfileWithUserIdPort.loadProfileWithUserId(new UserId(userId));

		base.follow(target.getUser());

		updateProfileStatePort.updateProfile(base);

		return ProfileResult.of(target, base.isFollowing(target.getUser()));
	}
}

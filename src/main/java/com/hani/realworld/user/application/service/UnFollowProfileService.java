package com.hani.realworld.user.application.service;

import javax.transaction.Transactional;

import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.user.application.port.in.UnFollowProfileUseCase;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@UseCase
public class UnFollowProfileService implements UnFollowProfileUseCase {

	@Override
	public ProfileResult unfollowProfile(String username, Long userId) {
		return null;
	}
}

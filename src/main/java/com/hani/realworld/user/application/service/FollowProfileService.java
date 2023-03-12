package com.hani.realworld.user.application.service;

import javax.transaction.Transactional;

import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.user.application.port.in.FollowProfileUseCase;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@UseCase
public class FollowProfileService implements FollowProfileUseCase {

	@Override
	public ProfileResult followProfile(String username, Long userId) {
		return null;
	}
}

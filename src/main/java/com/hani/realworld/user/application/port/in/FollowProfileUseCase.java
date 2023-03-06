package com.hani.realworld.user.application.port.in;

import static com.hani.realworld.user.domain.User.*;

import com.hani.realworld.user.application.port.in.result.ProfileResult;

public interface FollowProfileUseCase {
	ProfileResult followProfile(String username, UserId userId);
}

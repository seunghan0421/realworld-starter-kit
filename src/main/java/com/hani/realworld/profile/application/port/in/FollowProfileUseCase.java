package com.hani.realworld.profile.application.port.in;

import static com.hani.realworld.profile.domain.User.*;

import com.hani.realworld.profile.application.port.in.result.ProfileResult;
import com.hani.realworld.profile.domain.User;

public interface FollowProfileUseCase {
	ProfileResult followProfile(String username, UserId userId);
}

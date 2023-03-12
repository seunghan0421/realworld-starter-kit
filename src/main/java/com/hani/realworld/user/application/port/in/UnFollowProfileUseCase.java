package com.hani.realworld.user.application.port.in;

import com.hani.realworld.user.application.port.in.result.ProfileResult;

public interface UnFollowProfileUseCase {
	ProfileResult unfollowProfile(String username, Long userId);
}

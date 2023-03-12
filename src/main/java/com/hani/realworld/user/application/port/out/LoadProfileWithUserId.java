package com.hani.realworld.user.application.port.out;

import static com.hani.realworld.user.domain.User.*;

import com.hani.realworld.user.domain.Profile;

public interface LoadProfileWithUserId {
	Profile loadProfileWithUserId(UserId userId);
}

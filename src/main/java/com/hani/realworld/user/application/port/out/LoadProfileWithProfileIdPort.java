package com.hani.realworld.user.application.port.out;

import static com.hani.realworld.user.domain.Profile.*;

import com.hani.realworld.user.domain.Profile;

public interface LoadProfileWithProfileIdPort {
	Profile loadProfileWithProfileId(ProfileId profileId);
}

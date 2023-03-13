package com.hani.realworld.user.application.port.out;

import com.hani.realworld.user.domain.Profile;

public interface UpdateProfileStatePort {
	void updateProfile(Profile profile);
}

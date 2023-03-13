package com.hani.realworld.user.application.port.out;

import com.hani.realworld.user.domain.Profile;

public interface RegisterProfileStatePort {
	void register(Profile profile);
}

package com.hani.realworld.profile.application.port.out;

import com.hani.realworld.profile.domain.User;

public interface RegisterUserStatePort {
	void registerUser(User user);
}

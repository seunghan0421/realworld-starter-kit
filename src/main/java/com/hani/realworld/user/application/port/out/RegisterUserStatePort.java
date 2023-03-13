package com.hani.realworld.user.application.port.out;

import com.hani.realworld.user.domain.User;

public interface RegisterUserStatePort {
	void register(User user);
}

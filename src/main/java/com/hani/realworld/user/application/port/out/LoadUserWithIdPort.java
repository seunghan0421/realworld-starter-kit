package com.hani.realworld.user.application.port.out;

import static com.hani.realworld.user.domain.User.*;

import com.hani.realworld.user.domain.User;

public interface LoadUserWithIdPort {
	User loadUserWithId(UserId userId);
}

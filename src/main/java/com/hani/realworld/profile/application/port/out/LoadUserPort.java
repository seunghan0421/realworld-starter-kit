package com.hani.realworld.profile.application.port.out;

import static com.hani.realworld.profile.domain.User.*;

import com.hani.realworld.profile.domain.User;

public interface LoadUserPort {
	User loadUser(UserId userId);
}

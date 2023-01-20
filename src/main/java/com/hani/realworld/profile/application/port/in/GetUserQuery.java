package com.hani.realworld.profile.application.port.in;

import static com.hani.realworld.profile.domain.User.*;

import javax.validation.constraints.NotNull;

import com.hani.realworld.profile.application.port.in.result.UserResult;

public interface GetUserQuery {
	UserResult getUser(@NotNull UserId userId);
}

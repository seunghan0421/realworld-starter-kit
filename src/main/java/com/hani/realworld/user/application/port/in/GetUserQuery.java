package com.hani.realworld.user.application.port.in;

import static com.hani.realworld.user.domain.User.*;

import javax.validation.constraints.NotNull;

import com.hani.realworld.user.application.port.in.result.UserResult;

public interface GetUserQuery {
	UserResult getUser(@NotNull UserId userId);
}

package com.hani.realworld.user.application.port.in;

import javax.validation.constraints.NotNull;

import com.hani.realworld.user.application.port.in.result.UserResult;

public interface GetUserQuery {
	UserResult getUser(@NotNull Long userId);
}

package com.hani.realworld.profile.application.port.in;

import static com.hani.realworld.profile.domain.User.*;

import com.hani.realworld.profile.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.profile.application.port.in.result.UserResult;

public interface UpdateUserUseCase {
	UserResult updateUser(UserId userId, UpdateUserCommand command);
}

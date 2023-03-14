package com.hani.realworld.user.application.port.in;

import com.hani.realworld.user.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;
import com.hani.realworld.user.application.port.in.result.UserResult;

public interface UpdateUserUseCase {
	UserResult updateUser(UpdateUserCommand command, Long userId);
}

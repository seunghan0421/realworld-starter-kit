package com.hani.realworld.user.application.port.in;

import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.user.application.port.in.result.UserResult;

public interface RegisterUserUseCase {
	UserResult register(RegisterUserCommand command);
}

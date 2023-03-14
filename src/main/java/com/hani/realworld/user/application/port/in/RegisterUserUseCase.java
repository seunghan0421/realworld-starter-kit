package com.hani.realworld.user.application.port.in;

import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;

public interface RegisterUserUseCase {
	LoginUserResult register(RegisterUserCommand command);
}

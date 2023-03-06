package com.hani.realworld.user.application.port.in;

import com.hani.realworld.user.application.port.in.command.LoginUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;

public interface LoginUserUseCase {
	LoginUserResult login(LoginUserCommand command);
}

package com.hani.realworld.profile.application.port.in;

import com.hani.realworld.profile.application.port.in.command.LoginUserCommand;
import com.hani.realworld.profile.application.port.in.result.LoginUserResult;
import com.hani.realworld.profile.application.port.in.result.UserResult;

public interface LoginUserUseCase {
	LoginUserResult login(LoginUserCommand command);
}

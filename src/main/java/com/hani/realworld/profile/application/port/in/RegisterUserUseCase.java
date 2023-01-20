package com.hani.realworld.profile.application.port.in;

import com.hani.realworld.profile.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.profile.application.port.in.result.UserResult;
import com.hani.realworld.profile.domain.User;

public interface RegisterUserUseCase {
	UserResult register(RegisterUserCommand command);
}

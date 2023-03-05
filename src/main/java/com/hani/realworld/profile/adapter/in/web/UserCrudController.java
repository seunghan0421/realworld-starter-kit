package com.hani.realworld.profile.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.profile.adapter.in.web.dto.RegisterUserRequest;
import com.hani.realworld.profile.application.port.in.GetUserQuery;
import com.hani.realworld.profile.application.port.in.RegisterUserUseCase;
import com.hani.realworld.profile.application.port.in.UpdateUserUseCase;
import com.hani.realworld.profile.application.port.in.result.UserResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserCrudController {

	private final RegisterUserUseCase registerUserUseCase;
	private final UpdateUserUseCase updateUserUseCase;

	private final GetUserQuery getUserQuery;

	@PostMapping("/api/users")
	UserResult register(@RequestBody RegisterUserRequest request) {
		return registerUserUseCase.register(request.toCommand());
	}
}

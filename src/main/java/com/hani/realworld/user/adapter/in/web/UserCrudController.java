package com.hani.realworld.user.adapter.in.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.infra.jwt.SecurityUser;
import com.hani.realworld.user.adapter.in.web.dto.RegisterUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.UpdateUserRequest;
import com.hani.realworld.user.application.port.in.GetUserQuery;
import com.hani.realworld.user.application.port.in.RegisterUserUseCase;
import com.hani.realworld.user.application.port.in.UpdateUserUseCase;
import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.user.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.user.application.port.in.result.UserResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserCrudController {

	private final RegisterUserUseCase registerUserUseCase;
	private final UpdateUserUseCase updateUserUseCase;

	private final GetUserQuery getUserQuery;

	@PostMapping("/api/users")
	UserResult registerUser(@RequestBody RegisterUserRequest request) {

		RegisterUserCommand command = new RegisterUserCommand(
			request.getUsername(),
			request.getEmail(),
			request.getPassword());

		return registerUserUseCase.register(command);
	}

	@GetMapping("/api/users")
	UserResult getUser(@AuthenticationPrincipal SecurityUser user) {
		return getUserQuery.getUser(user.getId());
	}

	@PutMapping("/api/users")
	UserResult getUser(
		@AuthenticationPrincipal SecurityUser user,
		@RequestBody UpdateUserRequest request) {

		UpdateUserCommand command = new UpdateUserCommand(
			request.getEmail(),
			request.getUsername(),
			request.getPassword(),
			request.getImage(),
			request.getBio());

		return updateUserUseCase.updateUser(user.getId(), command);
	}
}

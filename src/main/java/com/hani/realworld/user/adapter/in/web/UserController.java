package com.hani.realworld.user.adapter.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.infra.jwt.LoginToken;
import com.hani.realworld.infra.jwt.LoginUser;
import com.hani.realworld.user.adapter.in.web.dto.response.RegisterUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.request.UpdateUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.response.UserResponse;
import com.hani.realworld.user.application.port.in.GetUserQuery;
import com.hani.realworld.user.application.port.in.RegisterUserUseCase;
import com.hani.realworld.user.application.port.in.UpdateUserUseCase;
import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.user.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;
import com.hani.realworld.user.application.port.in.result.UserResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final RegisterUserUseCase registerUserUseCase;
	private final UpdateUserUseCase updateUserUseCase;

	private final GetUserQuery getUserQuery;

	@PostMapping("/api/users")
	ResponseEntity<UserResponse> registerUser(@RequestBody RegisterUserRequest request) {

		RegisterUserCommand command = new RegisterUserCommand(
			request.getUsername(),
			request.getEmail(),
			request.getPassword());

		UserResult userResult = registerUserUseCase.register(command);

		return ResponseEntity.created(URI.create("/api/profiles/" + request.getUsername()))
			.body(UserResponse.of(userResult, null));
	}

	@GetMapping("/api/user")
	ResponseEntity<UserResponse> getUser(@LoginUser LoginToken loginToken) {

		UserResult userResult = getUserQuery.getUser(loginToken.getId());

		return ResponseEntity.ok(UserResponse.of(userResult, loginToken.getToken()));
	}

	@PutMapping("/api/user")
	ResponseEntity<UserResponse> updateUser(
		@RequestBody UpdateUserRequest request,
		@LoginUser LoginToken loginToken) {

		UpdateUserCommand command = new UpdateUserCommand(
			request.getEmail(),
			request.getUsername(),
			request.getPassword(),
			request.getImage(),
			request.getBio());

		LoginUserResult userResult = updateUserUseCase.updateUser(command, loginToken.getId());

		return ResponseEntity.ok(UserResponse.of(userResult));
	}
}

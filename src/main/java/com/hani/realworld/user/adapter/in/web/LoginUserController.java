package com.hani.realworld.user.adapter.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.user.adapter.in.web.dto.LoginUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.RegisterUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.UserResponse;
import com.hani.realworld.user.application.port.in.LoginUserUseCase;
import com.hani.realworld.user.application.port.in.command.LoginUserCommand;
import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;
import com.hani.realworld.user.application.port.in.result.UserResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class LoginUserController {

	private final LoginUserUseCase loginUserUseCase;

	@PostMapping("/api/users/login")
	ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request) {

		LoginUserCommand command = new LoginUserCommand(
			request.getEmail(),
			request.getPassword());

		LoginUserResult userResult = loginUserUseCase.login(command);

		return ResponseEntity.ok(UserResponse.of(userResult));
	}

}

package com.hani.realworld.user.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;

import lombok.Getter;

@Getter
@JsonRootName("user")
public class RegisterUserRequest {
	private String username;
	private String email;
	private String password;
}

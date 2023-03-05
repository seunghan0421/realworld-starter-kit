package com.hani.realworld.profile.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.hani.realworld.profile.application.port.in.command.RegisterUserCommand;

import lombok.Getter;

@Getter
@JsonRootName("user")
public class RegisterUserRequest {

	private String username;
	private String email;
	private String password;

	public RegisterUserCommand toCommand() {
		return new RegisterUserCommand(
			this.username,
			this.email,
			this.password);
	}

}

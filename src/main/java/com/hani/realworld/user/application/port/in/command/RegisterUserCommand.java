package com.hani.realworld.user.application.port.in.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.hani.realworld.common.util.SelfValidating;

import lombok.Value;

@Value
public class RegisterUserCommand extends SelfValidating<RegisterUserCommand> {

	@NotBlank(message = "username cannot be empty.")
	private String username;

	@NotBlank(message = "email cannot be empty.")
	@Email(message = "email address is not in a valid format.")
	private String email;

	@NotBlank(message = "password cannot be empty.")
	private String password;

	public RegisterUserCommand(
		String username,
		String email,
		String password) {
		this.username = username;
		this.email = email;
		this.password = password;

		this.validateSelf();
	}
}

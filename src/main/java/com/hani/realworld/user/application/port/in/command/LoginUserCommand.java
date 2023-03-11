package com.hani.realworld.user.application.port.in.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.hani.realworld.common.util.SelfValidating;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class LoginUserCommand extends SelfValidating<LoginUserCommand> {

	@NotBlank(message = "email cannot be empty.")
	@Email(message = "email address is not in a valid format.")
	private final String email;

	@NotBlank(message = "password cannot be empty.")
	private final String password;

	public LoginUserCommand(String email, String password) {
		this.email = email;
		this.password = password;

		this.validateSelf();
	}
}

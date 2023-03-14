package com.hani.realworld.user.application.port.in.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.hani.realworld.common.util.SelfValidating;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class RegisterUserCommand extends SelfValidating<RegisterUserCommand> {

	@NotBlank(message = "username cannot be empty.")
	private String username;

	@NotBlank(message = "email cannot be empty.")
	@Email(message = "email address is not in a valid format.")
	private String email;

	@NotBlank(message = "password cannot be empty.")
	@Length(min = 1, max = 16, message = "password length must be between 1 and 16")
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

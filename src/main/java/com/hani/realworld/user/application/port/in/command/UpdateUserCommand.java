package com.hani.realworld.user.application.port.in.command;

import javax.validation.constraints.Email;

import com.hani.realworld.common.util.SelfValidating;

import lombok.Value;

@Value
public class UpdateUserCommand extends SelfValidating<UpdateUserCommand> {

	@Email(message = "email address is not in a valid format.")
	private String email;
	private String username;
	private String password;
	private String image;
	private String bio;

	public UpdateUserCommand(String email, String username, String password, String image, String bio) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.image = image;
		this.bio = bio;

		this.validateSelf();
	}
}

package com.hani.realworld.profile.application.port.in.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.hani.realworld.common.util.SelfValidating;

import lombok.EqualsAndHashCode;
import lombok.Value;



@Value
@EqualsAndHashCode(callSuper = true)
public class UpdateUserCommand extends SelfValidating<UpdateUserCommand> {

	@Email(message = "email address is not in a valid format.")
	private final String email;
	private final String username;
	private final String password;
	private final String image;
	private final String bio;

	public UpdateUserCommand(String email, String username, String password, String image, String bio) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.image = image;
		this.bio = bio;

		this.validateSelf();
	}
}

package com.hani.realworld.user.application.port.in.command;

import static com.hani.realworld.common.util.PreConditions.*;

import java.util.Optional;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import com.hani.realworld.common.util.SelfValidating;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
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
		Optional.ofNullable(password)
			.ifPresent(pass -> checkStringHasValidLength(1, 16, pass));
	}
}

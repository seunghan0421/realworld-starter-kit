package com.hani.realworld.comment.application.port.in.command;

import javax.validation.constraints.NotBlank;

import com.hani.realworld.common.util.SelfValidating;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class AddCommentCommand extends SelfValidating<AddCommentCommand> {

	@NotBlank(message = "comment body cannot be empty.")
	private String body;

	public AddCommentCommand(String body) {
		this.body = body;

		this.validateSelf();
	}
}

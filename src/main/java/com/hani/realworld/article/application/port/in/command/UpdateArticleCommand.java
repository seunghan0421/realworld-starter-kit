package com.hani.realworld.article.application.port.in.command;

import javax.validation.constraints.Size;

import com.hani.realworld.common.util.SelfValidating;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class UpdateArticleCommand extends SelfValidating<UpdateArticleCommand> {

	@Size(min = 1, message = "article title cannot be empty.")
	private String title;

	@Size(min = 1, message = "article description cannot be empty.")
	private String description;

	@Size(min = 1, message = "article description cannot be empty.")
	private String body;

	public UpdateArticleCommand(String title, String description, String body) {
		this.title = title;
		this.description = description;
		this.body = body;

		this.validateSelf();
	}
}

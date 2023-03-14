package com.hani.realworld.article.application.port.in.command;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import com.hani.realworld.article.domain.Tags;
import com.hani.realworld.common.util.SelfValidating;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class CreateArticleCommand extends SelfValidating<CreateArticleCommand> {

	@NotBlank(message = "article title cannot be empty.")
	private String title;

	@NotBlank(message = "article description cannot be empty.")
	private String description;

	@NotBlank(message = "article description cannot be empty.")
	private String body;

	private Tags tags;

	public CreateArticleCommand(
		String title,
		String description,
		String body,
		List<String> tagList) {

		this.title = title;
		this.description = description;
		this.body = body;

		this.tags = Optional.ofNullable(tagList)
			.map(Tags::new)
			.orElseGet(Tags::new);

		this.validateSelf();
	}
}

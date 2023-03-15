package com.hani.realworld.article.application.port.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hani.realworld.article.application.port.in.command.UpdateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;

public interface UpdateArticleUseCase {
	ArticleResult update(@NotNull UpdateArticleCommand command, @NotBlank String slug, @NotNull Long userId);
}

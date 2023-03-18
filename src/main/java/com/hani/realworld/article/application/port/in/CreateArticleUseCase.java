package com.hani.realworld.article.application.port.in;

import javax.validation.constraints.NotNull;

import com.hani.realworld.article.application.port.in.command.CreateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;

public interface CreateArticleUseCase {
	ArticleResult createArticle(@NotNull CreateArticleCommand command, @NotNull Long userId);
}

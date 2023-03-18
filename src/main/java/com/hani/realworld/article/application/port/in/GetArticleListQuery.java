package com.hani.realworld.article.application.port.in;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.hani.realworld.article.application.port.in.command.GetListArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;

public interface GetArticleListQuery {
	List<ArticleResult> getArticleList(@NotNull GetListArticleCommand command, @NotNull Optional<Long> userId);
}

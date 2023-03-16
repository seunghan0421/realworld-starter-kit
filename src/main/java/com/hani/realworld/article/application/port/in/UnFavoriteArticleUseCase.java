package com.hani.realworld.article.application.port.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hani.realworld.article.application.port.in.result.ArticleResult;

public interface UnFavoriteArticleUseCase {
	ArticleResult unFavoriteArticle(@NotBlank String slug, @NotNull Long userId);
}

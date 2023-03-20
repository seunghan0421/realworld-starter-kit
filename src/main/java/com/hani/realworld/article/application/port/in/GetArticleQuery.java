package com.hani.realworld.article.application.port.in;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import com.hani.realworld.article.application.port.in.result.ArticleResult;

public interface GetArticleQuery {
	ArticleResult getArticle(@NotBlank String slug, Optional<Long> userId);
}

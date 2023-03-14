package com.hani.realworld.article.application.service;

import java.util.Optional;

import com.hani.realworld.article.application.port.in.GetArticleQuery;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.common.annotation.Query;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetArticleService implements GetArticleQuery {

	@Override
	public ArticleResult getArticle(String slug, Optional<Long> userId) {
		return null;
	}
}

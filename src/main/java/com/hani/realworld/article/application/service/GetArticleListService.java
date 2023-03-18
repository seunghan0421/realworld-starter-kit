package com.hani.realworld.article.application.service;

import java.util.List;
import java.util.Optional;

import com.hani.realworld.article.application.port.in.GetArticleListQuery;
import com.hani.realworld.article.application.port.in.command.GetListArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.common.annotation.Query;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetArticleListService implements GetArticleListQuery {
	@Override
	public List<ArticleResult> getArticleList(GetListArticleCommand command, Optional<Long> userId) {
		return null;
	}
}

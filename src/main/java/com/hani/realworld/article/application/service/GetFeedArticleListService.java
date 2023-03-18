package com.hani.realworld.article.application.service;

import java.util.List;

import com.hani.realworld.article.application.port.in.GetFeedArticleListQuery;
import com.hani.realworld.article.application.port.in.command.PagingParam;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.common.annotation.Query;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetFeedArticleListService implements GetFeedArticleListQuery {
	@Override
	public List<ArticleResult> getFeedArticleList(PagingParam pagingParam, Long userId) {
		return null;
	}
}

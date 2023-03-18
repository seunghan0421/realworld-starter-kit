package com.hani.realworld.article.application.port.in;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.hani.realworld.article.application.port.in.command.PagingParam;
import com.hani.realworld.article.application.port.in.result.ArticleResult;

public interface GetFeedArticleListQuery {
	List<ArticleResult> getFeedArticleList(@NotNull PagingParam pagingParam, @NotNull Long userId);
}

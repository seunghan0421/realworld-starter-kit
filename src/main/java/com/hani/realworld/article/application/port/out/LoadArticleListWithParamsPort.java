package com.hani.realworld.article.application.port.out;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.hani.realworld.article.application.port.in.command.PagingParam;
import com.hani.realworld.article.domain.Article;

public interface LoadArticleListWithParamsPort {
	List<Article> loadArticleList(@NotNull PagingParam pagingParam, String tag, String author, String favorited);
}

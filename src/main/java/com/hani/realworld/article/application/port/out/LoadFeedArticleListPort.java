package com.hani.realworld.article.application.port.out;

import static com.hani.realworld.user.domain.User.*;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.hani.realworld.article.application.port.in.command.PagingParam;
import com.hani.realworld.article.domain.Article;

public interface LoadFeedArticleListPort {
	List<Article> loadFeedArticleList(@NotNull PagingParam pagingParam, UserId userId);
}

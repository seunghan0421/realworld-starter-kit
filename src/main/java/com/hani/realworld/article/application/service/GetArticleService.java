package com.hani.realworld.article.application.service;

import static com.hani.realworld.user.domain.User.*;

import java.util.Optional;

import com.hani.realworld.article.application.port.in.GetArticleQuery;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.common.annotation.Query;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetArticleService implements GetArticleQuery {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort;

	private final GetProfileQuery getProfileQuery;

	@Override
	public ArticleResult getArticle(String slug, Optional<Long> userId) {
		Article article = loadArticleWithSlugPort.load(slug);
		ProfileResult author = getProfileQuery.getProfile(article.getAuthor().getUser().getUsername(), userId);

		boolean isFavorited = userId.map(UserId::new).filter(article::isFavorite).isPresent();
		int favoritesCount = article.getFavorites().getFavorites().size();

		return ArticleResult.of(article, author, isFavorited, favoritesCount);
	}
}

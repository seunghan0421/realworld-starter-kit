package com.hani.realworld.article.application.service;

import static com.hani.realworld.user.domain.User.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hani.realworld.article.application.port.in.GetFeedArticleListQuery;
import com.hani.realworld.article.application.port.in.command.PagingParam;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.LoadFeedArticleListPort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.common.annotation.Query;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetFeedArticleListService implements GetFeedArticleListQuery {

	private final LoadFeedArticleListPort loadFeedArticleListPort;

	private final GetProfileQuery getProfileQuery;

	@Override
	public List<ArticleResult> getFeedArticleList(PagingParam pagingParam, Long userId) {

		List<Article> articles = loadFeedArticleListPort.loadFeedArticleList(pagingParam, new UserId(userId));

		return articles.stream()
			.map(article -> {
				ProfileResult profileResult =
					getProfileQuery.getProfile(article.getAuthor().getUser().getUsername(), Optional.of(userId));

				boolean isFavorited = Optional.of(userId).map(UserId::new).filter(article::isFavorite).isPresent();
				int favoritesCount = article.getFavorites().getFavorites().size();

				return ArticleResult.of(article, profileResult, isFavorited, favoritesCount);
			})
			.collect(Collectors.toList());
	}
}

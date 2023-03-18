package com.hani.realworld.article.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hani.realworld.article.application.port.in.GetArticleListQuery;
import com.hani.realworld.article.application.port.in.command.GetListArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.LoadArticleListWithParamsPort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.common.annotation.Query;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetArticleListService implements GetArticleListQuery {

	private final LoadArticleListWithParamsPort loadArticleListWithParamsPort;

	private final GetProfileQuery getProfileQuery;

	@Override
	public List<ArticleResult> getArticleList(GetListArticleCommand command, Optional<Long> userId) {

		List<Article> articles = loadArticleListWithParamsPort.loadArticleList(
			command.getPagingParam(),
			command.getTag(),
			command.getAuthor(),
			command.getFavorited());

		return articles.stream()
			.map(article -> {
				ProfileResult profileResult =
					getProfileQuery.getProfile(article.getAuthor().getUser().getUsername(), userId);

				boolean isFavorited = userId.map(User.UserId::new).filter(article::isFavorite).isPresent();
				int favoritesCount = article.getFavorites().getFavorites().size();

				return ArticleResult.of(article, profileResult, isFavorited, favoritesCount);
			})
			.collect(Collectors.toList());
	}
}

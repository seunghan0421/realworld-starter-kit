package com.hani.realworld.article.application.service;

import static com.hani.realworld.user.domain.User.*;

import java.util.Optional;

import javax.transaction.Transactional;

import com.hani.realworld.article.application.port.in.UpdateArticleUseCase;
import com.hani.realworld.article.application.port.in.command.UpdateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.application.port.out.UpdateArticleStatePort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class UpdateArticleService implements UpdateArticleUseCase {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort;
	private final UpdateArticleStatePort updateArticleStatePort;

	private final GetProfileQuery getProfileQuery;

	@Override
	public ArticleResult update(UpdateArticleCommand command, String slug, Long userId) {
		Article article = loadArticleWithSlugPort.load(slug);

		article.checkisMyArticle(new UserId(userId));

		Article updatedArticle = article.update(
			command.getTitle(),
			command.getDescription(),
			command.getBody());

		updateArticleStatePort.update(updatedArticle);

		ProfileResult profileResult = getProfileQuery.getProfile(
			article.getAuthor().getUser().getUsername(),
			Optional.of(userId));

		boolean isFavorited = article.isFavorite(new UserId(userId));
		int favoritesCount = article.getFavorites().getFavorites().size();

		return ArticleResult.of(updatedArticle, profileResult, isFavorited, favoritesCount);
	}

}


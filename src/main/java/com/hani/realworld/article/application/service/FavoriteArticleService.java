package com.hani.realworld.article.application.service;

import static com.hani.realworld.user.domain.User.*;

import java.util.Optional;

import javax.transaction.Transactional;

import com.hani.realworld.article.application.port.in.FavoriteArticleUseCase;
import com.hani.realworld.article.application.port.in.GetArticleQuery;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.application.port.out.UpdateArticleStatePort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class FavoriteArticleService implements FavoriteArticleUseCase {

	private final UpdateArticleStatePort updateArticleStatePort;
	private final LoadArticleWithSlugPort loadArticleWithSlugPort;

	private final GetArticleQuery getArticleQuery;

	@Override
	public ArticleResult favoriteArticle(String slug, Long userId) {
		Article article = loadArticleWithSlugPort.load(slug);
		article.favorite(new UserId(userId));

		updateArticleStatePort.update(article);

		return getArticleQuery.getArticle(slug, Optional.of(userId));
	}
}

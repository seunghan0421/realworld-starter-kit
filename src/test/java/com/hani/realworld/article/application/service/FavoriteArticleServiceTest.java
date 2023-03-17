package com.hani.realworld.article.application.service;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.in.GetArticleQuery;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.application.port.out.UpdateArticleStatePort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

class FavoriteArticleServiceTest {

	private final UpdateArticleStatePort updateArticleStatePort =
		Mockito.mock(UpdateArticleStatePort.class);

	private final LoadArticleWithSlugPort loadArticleWithSlugPort =
		Mockito.mock(LoadArticleWithSlugPort.class);

	private final GetArticleQuery getArticleQuery =
		Mockito.mock(GetArticleQuery.class);

	private final FavoriteArticleService favoriteArticleService =
		new FavoriteArticleService(updateArticleStatePort, loadArticleWithSlugPort, getArticleQuery);

	@Test
	void favoriteArticle_Succeeds() {
		// given
		Article article = getMockARTICLE1();
		ArticleResult response = ArticleResult.of(
			ARTICLE1,
			ProfileResult.of(ARTICLE1.getAuthor(), false),
			true,
			1);

		final String article1Slug = ARTICLE1.getSlug().getSlug();
		final Long user2Id = USER2.getId().getValue();

		given(loadArticleWithSlugPort.load(eq(article1Slug)))
			.willReturn(article);
		given(getArticleQuery.getArticle(eq(article1Slug), eq(Optional.of(user2Id))))
			.willReturn(response);

		// when
		ArticleResult result = favoriteArticleService.favoriteArticle(article1Slug, user2Id);

		// then
		assertThat(result.getSlug()).isEqualTo(article1Slug);
		assertThat(result.isFavorited()).isEqualTo(true);
		assertThat(result.getFavoritesCount()).isEqualTo(1);

		then(loadArticleWithSlugPort).should().load(eq(article1Slug));
		then(article).should().favorite(eq(USER2.getId()));
		then(updateArticleStatePort).should().update(any());
		then(getArticleQuery).should().getArticle(eq(article1Slug), eq(Optional.of(user2Id)));
	}

}

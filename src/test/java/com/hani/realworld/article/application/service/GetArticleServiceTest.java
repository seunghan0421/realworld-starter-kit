package com.hani.realworld.article.application.service;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

class GetArticleServiceTest {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort =
		Mockito.mock(LoadArticleWithSlugPort.class);

	private final GetProfileQuery getProfileQuery =
		Mockito.mock(GetProfileQuery.class);

	private final GetArticleService getArticleService =
		new GetArticleService(loadArticleWithSlugPort, getProfileQuery);

	@Test
	void getArticle_Succeeds() {
		// given
		ProfileResult response = ProfileResult.of(ARTICLE1.getAuthor(), false);

		final String slug = ARTICLE1.getSlug().getSlug();
		final String authorName = ARTICLE1.getAuthor().getUser().getUsername();

		given(loadArticleWithSlugPort.load(eq(slug))).willReturn(ARTICLE1);
		given(getProfileQuery.getProfile(eq(authorName), any())).willReturn(response);

		// when
		ArticleResult result = getArticleService.getArticle(slug, Optional.of(USER1.getId().getValue()));

		// then
		assertThat(result.getSlug()).isEqualTo(ARTICLE1.getSlug().getSlug());
		assertThat(result.getTitle()).isEqualTo(ARTICLE1.getTitle());
		assertThat(result.getDescription()).isEqualTo(ARTICLE1.getDescription());
		assertThat(result.getBody()).isEqualTo(ARTICLE1.getBody());
		assertThat(result.getCreatedAt()).isNotNull();
		assertThat(result.getUpdatedAt()).isNotNull();

		then(loadArticleWithSlugPort).should().load(eq(slug));
		then(getProfileQuery).should().getProfile(eq(authorName), eq(Optional.of(USER1.getId().getValue())));
	}

}

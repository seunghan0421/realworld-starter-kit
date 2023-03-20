package com.hani.realworld.article.application.service;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.in.command.UpdateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.application.port.out.UpdateArticleStatePort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

class UpdateArticleServiceTest {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort =
		Mockito.mock(LoadArticleWithSlugPort.class);

	private final UpdateArticleStatePort updateArticleStatePort =
		Mockito.mock(UpdateArticleStatePort.class);

	private final GetProfileQuery getProfileQuery =
		Mockito.mock(GetProfileQuery.class);

	private final UpdateArticleService updateArticleService =
		new UpdateArticleService(loadArticleWithSlugPort, updateArticleStatePort, getProfileQuery);

	@DisplayName("게시물 정보 수정 서비스 테스트 - 성공")
	@Test
	void updateArticle_Succeeds() {
		// given
		Article article = getMockARTICLE1();
		ProfileResult response = ProfileResult.of(ARTICLE1.getAuthor(), false);

		final String slug = ARTICLE1.getSlug().getValue();
		final String authorName = ARTICLE1.getAuthor().getUser().getUsername();

		given(loadArticleWithSlugPort.load(eq(slug)))
			.willReturn(article);
		given(getProfileQuery.getProfile(eq(authorName), eq(Optional.of(USER1.getId().getValue()))))
			.willReturn(response);
		given(article.update(any(), any(), any()))
			.willReturn(defaultArticle().
				withTitle(ARTICLE2.getTitle()).
				withDescription(ARTICLE2.getDescription()).
				withBody(ARTICLE2.getBody())
				.build());

		// when
		ArticleResult result = updateArticleService.updateArticle(
			new UpdateArticleCommand(ARTICLE2.getTitle(), ARTICLE2.getDescription(), ARTICLE2.getBody()),
			slug,
			USER1.getId().getValue());

		// then
		assertThat(result.getSlug()).isEqualTo(ARTICLE2.getSlug().getValue());
		assertThat(result.getTitle()).isEqualTo(ARTICLE2.getTitle());
		assertThat(result.getDescription()).isEqualTo(ARTICLE2.getDescription());
		assertThat(result.getBody()).isEqualTo(ARTICLE2.getBody());

		then(loadArticleWithSlugPort).should().load(eq(slug));
		then(article).should().checkisMyArticle(eq(USER1.getId()));
		then(article).should().update(eq(ARTICLE2.getTitle()), eq(ARTICLE2.getDescription()), eq(ARTICLE2.getBody()));
		then(updateArticleStatePort).should().update(any());
		then(getProfileQuery).should().getProfile(eq(authorName), eq(Optional.of(USER1.getId().getValue())));
	}

}

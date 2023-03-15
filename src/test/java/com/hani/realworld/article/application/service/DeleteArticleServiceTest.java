package com.hani.realworld.article.application.service;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.out.DeleteArticleWithArticleId;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.domain.Article;

class DeleteArticleServiceTest {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort =
		Mockito.mock(LoadArticleWithSlugPort.class);

	private final DeleteArticleWithArticleId deleteArticleWithArticleId =
		Mockito.mock(DeleteArticleWithArticleId.class);

	private final DeleteArticleService deleteArticleService =
		new DeleteArticleService(loadArticleWithSlugPort, deleteArticleWithArticleId);

	@Test
	void deleteArticle_Succeeds() {
		// given
		Article article = getMockARTICLE1();

		final String slug = ARTICLE1.getSlug().getSlug();

		given(loadArticleWithSlugPort.load(eq(slug))).willReturn(article);

		// when
		deleteArticleService.delete(slug, USER1.getId().getValue());

		// then
		then(loadArticleWithSlugPort).should().load(eq(slug));
		then(article).should().checkisMyArticle(eq(USER1.getId()));
		then(deleteArticleWithArticleId).should().delete(eq(ARTICLE1.getId()));
	}

}

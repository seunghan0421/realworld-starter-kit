package com.hani.realworld.article.application.service;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.out.DeleteArticleWithArticleIdPort;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.domain.Article;

class DeleteArticleServiceTest {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort =
		Mockito.mock(LoadArticleWithSlugPort.class);

	private final DeleteArticleWithArticleIdPort deleteArticleWithArticleIdPort =
		Mockito.mock(DeleteArticleWithArticleIdPort.class);

	private final DeleteArticleService deleteArticleService =
		new DeleteArticleService(loadArticleWithSlugPort, deleteArticleWithArticleIdPort);

	@DisplayName("게시물 삭제 서비스 테스트 - 성공")
	@Test
	void deleteArticle_Succeeds() {
		// given
		Article article = getMockARTICLE1();

		given(loadArticleWithSlugPort.load(eq(ARTICLE1.getSlug().getValue()))).willReturn(article);

		// when
		deleteArticleService.deleteArticle(ARTICLE1.getSlug().getValue(), USER1.getId().getValue());

		// then
		then(loadArticleWithSlugPort).should().load(eq(ARTICLE1.getSlug().getValue()));
		then(article).should().checkisMyArticle(eq(USER1.getId()));
		then(deleteArticleWithArticleIdPort).should().delete(eq(ARTICLE1.getId()));
	}

}

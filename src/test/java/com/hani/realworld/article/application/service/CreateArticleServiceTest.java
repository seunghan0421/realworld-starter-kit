package com.hani.realworld.article.application.service;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.in.command.CreateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.CreateArticleStatePort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;

class CreateArticleServiceTest {

	private final CreateArticleStatePort createArticleStatePort =
		Mockito.mock(CreateArticleStatePort.class);

	private final LoadProfileWithUserIdPort loadProfileWithUserIdPort =
		Mockito.mock(LoadProfileWithUserIdPort.class);

	private final CreateArticleService createArticleService =
		new CreateArticleService(createArticleStatePort, loadProfileWithUserIdPort);

	@DisplayName("게시물 생성 서비스 테스트 - 성공")
	@Test
	void createArticle_Succeeds() {
		// given
		Article article = ARTICLE1;

		CreateArticleCommand command = new CreateArticleCommand(
			ARTICLE1.getTitle(),
			ARTICLE1.getDescription(),
			ARTICLE1.getBody(),
			List.of("user1"));

		MockedStatic<Article> mockArticleClass = mockStatic(Article.class);
		mockArticleClass.when(() -> Article.withoutId(any(), any(), any(),any(), any()))
			.thenReturn(article);

		given(loadProfileWithUserIdPort.loadProfileWithUserId(eq(USER1.getId())))
			.willReturn(PROFILE1);

		// when
		ArticleResult result = createArticleService.createArticle(command, USER1.getId().getValue());

		// then
		assertThat(result.getSlug()).isEqualTo(ARTICLE1.getSlug().getValue());
		assertThat(result.getTitle()).isEqualTo(ARTICLE1.getTitle());
		assertThat(result.getDescription()).isEqualTo(ARTICLE1.getDescription());
		assertThat(result.getBody()).isEqualTo(ARTICLE1.getBody());
		assertThat(result.getTagList()).hasSize(1).contains("user1");
		assertThat(result.getCreatedAt()).isNotNull();
		assertThat(result.getUpdatedAt()).isNotNull();

		then(loadProfileWithUserIdPort).should().loadProfileWithUserId(eq(USER1.getId()));
		then(createArticleStatePort).should().create(eq(article));

		mockArticleClass.close();
	}

}

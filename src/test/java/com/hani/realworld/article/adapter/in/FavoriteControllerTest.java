package com.hani.realworld.article.adapter.in;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import com.hani.realworld.article.application.port.in.FavoriteArticleUseCase;
import com.hani.realworld.article.application.port.in.UnFavoriteArticleUseCase;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.common.descriptor.ArticleFieldDescriptor;
import com.hani.realworld.common.descriptor.ProfileFieldDescriptor;
import com.hani.realworld.ControllerTest;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

@WebMvcTest(FavoriteController.class)
class FavoriteControllerTest extends ControllerTest {

	@MockBean
	private FavoriteArticleUseCase favoriteArticleUseCase;

	@MockBean
	private UnFavoriteArticleUseCase unFavoriteArticleUseCase;

	@DisplayName("게시물 즐겨찾기 Controller Test")
	@Test
	void favoriteArticle_Succeeds() throws Exception {
		final ArticleResult response = ArticleResult.of(
			ARTICLE2,
			ProfileResult.of(ARTICLE2.getAuthor(), false),
			true,
			1);

		given(favoriteArticleUseCase.favoriteArticle(eq(ARTICLE2.getSlug().getValue()), eq(USER1.getId().getValue())))
			.willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/articles/{slug}/favorite", ARTICLE2.getSlug().getValue())
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName(AUTHORIZATION_HEADER_NAME).description("토큰")
					),
					pathParameters(
						parameterWithName("slug").description("게시물 슬러그")
					),
					responseFields(
						fieldWithPath("article").type(JsonFieldType.OBJECT).description("기사 정보")
					).andWithPrefix("article.", ArticleFieldDescriptor.article)
						.andWithPrefix("article.author.", ProfileFieldDescriptor.profile)
				)
			);

		then(favoriteArticleUseCase).should()
			.favoriteArticle(eq(ARTICLE2.getSlug().getValue()), eq(USER1.getId().getValue()));
	}

	@DisplayName("게시물 즐겨찾기 취소 Controller Test")
	@Test
	void unFavoriteArticle_Succeeds() throws Exception {
		final ArticleResult response = ArticleResult.of(
			ARTICLE2,
			ProfileResult.of(ARTICLE2.getAuthor(), false),
			false,
			0);

		given(unFavoriteArticleUseCase.unFavoriteArticle(eq(ARTICLE2.getSlug().getValue()), eq(USER1.getId().getValue())))
			.willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.delete("/api/articles/{slug}/favorite", ARTICLE2.getSlug().getValue())
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName(AUTHORIZATION_HEADER_NAME).description("토큰")
					),
					pathParameters(
						parameterWithName("slug").description("게시물 슬러그")
					),
					responseFields(
						fieldWithPath("article").type(JsonFieldType.OBJECT).description("기사 정보")
					).andWithPrefix("article.", ArticleFieldDescriptor.article)
						.andWithPrefix("article.author.", ProfileFieldDescriptor.profile)
				)
			);

		then(unFavoriteArticleUseCase).should()
			.unFavoriteArticle(eq(ARTICLE2.getSlug().getValue()), eq(USER1.getId().getValue()));
	}

}

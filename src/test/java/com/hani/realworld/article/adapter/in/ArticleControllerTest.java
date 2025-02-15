package com.hani.realworld.article.adapter.in;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import com.hani.realworld.article.application.port.in.CreateArticleUseCase;
import com.hani.realworld.article.application.port.in.DeleteArticleUseCase;
import com.hani.realworld.article.application.port.in.UpdateArticleUseCase;
import com.hani.realworld.article.application.port.in.command.CreateArticleCommand;
import com.hani.realworld.article.application.port.in.command.UpdateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.common.descriptor.ArticleFieldDescriptor;
import com.hani.realworld.common.descriptor.ProfileFieldDescriptor;
import com.hani.realworld.ControllerTest;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest extends ControllerTest {

	@MockBean
	private CreateArticleUseCase createArticleUseCase;

	@MockBean
	private UpdateArticleUseCase updateArticleUseCase;

	@MockBean
	private DeleteArticleUseCase deleteArticleUseCase;

	@DisplayName("게시물 생성 Controller Test")
	@Test
	void createArticle_Succeeds() throws Exception {
		String request = createJson(CREATE_ARTICLE_REQUEST);
		final ArticleResult response = ArticleResult.of(ARTICLE1, ProfileResult.of(ARTICLE1.getAuthor(), false), false,
			0);

		given(createArticleUseCase.createArticle(any(CreateArticleCommand.class), eq(USER1.getId().getValue())))
			.willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/articles")
					.accept(APPLICATION_JSON_VALUE)
					.contentType(MediaType.APPLICATION_JSON)
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
					.content(request)
			)
			.andExpect(status().isCreated())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName(AUTHORIZATION_HEADER_NAME).description("토큰")
					),
					requestFields(
						fieldWithPath("article.title").type(JsonFieldType.STRING).description("제목"),
						fieldWithPath("article.description").type(JsonFieldType.STRING).description("설명"),
						fieldWithPath("article.body").type(JsonFieldType.STRING).description("본문"),
						fieldWithPath("article.tagList").type(JsonFieldType.ARRAY).description("태그 리스트")
					),
					responseFields(
						fieldWithPath("article").type(JsonFieldType.OBJECT).description("기사 정보")
					).andWithPrefix("article.", ArticleFieldDescriptor.article)
						.andWithPrefix("article.author.", ProfileFieldDescriptor.profile)
				)
			);

		then(createArticleUseCase).should()
			.createArticle(eq(new CreateArticleCommand(
					CREATE_ARTICLE_REQUEST.getTitle(),
					CREATE_ARTICLE_REQUEST.getDescription(),
					CREATE_ARTICLE_REQUEST.getBody(),
					CREATE_ARTICLE_REQUEST.getTagList())),
				eq(USER1.getId().getValue()));
	}

	@DisplayName("게시물 수정 Controller Test")
	@Test
	void updateArticle_Succeeds() throws Exception {
		String request = createJson(UPDATE_ARTICLE_REQUEST);
		ArticleResult response = ArticleResult.of(
			defaultArticle()
				.withTitle(UPDATE_ARTICLE_REQUEST.getTitle())
				.withDescription(UPDATE_ARTICLE_REQUEST.getDescription())
				.withBody(UPDATE_ARTICLE_REQUEST.getBody())
				.build(),
			ProfileResult.of(ARTICLE1.getAuthor(), false),
			false, 2);

		given(updateArticleUseCase.updateArticle(any(UpdateArticleCommand.class), eq(ARTICLE1.getSlug().getValue()),
			eq(USER1.getId().getValue())))
			.willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.put("/api/articles/{slug}", ARTICLE1.getSlug().getValue())
					.accept(APPLICATION_JSON_VALUE)
					.contentType(MediaType.APPLICATION_JSON)
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
					.content(request)
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
					requestFields(
						fieldWithPath("article.title").type(JsonFieldType.STRING).description("제목"),
						fieldWithPath("article.description").type(JsonFieldType.STRING).description("설명"),
						fieldWithPath("article.body").type(JsonFieldType.STRING).description("본문")
					),
					responseFields(
						fieldWithPath("article").type(JsonFieldType.OBJECT).description("기사 정보")
					).andWithPrefix("article.", ArticleFieldDescriptor.article)
						.andWithPrefix("article.author.", ProfileFieldDescriptor.profile)
				)
			);

		then(updateArticleUseCase).should()
			.updateArticle(eq(new UpdateArticleCommand(
					UPDATE_ARTICLE_REQUEST.getTitle(),
					UPDATE_ARTICLE_REQUEST.getDescription(),
					UPDATE_ARTICLE_REQUEST.getBody())),
				eq(ARTICLE1.getSlug().getValue()),
				eq(USER1.getId().getValue()));
	}

	@DisplayName("게시물 삭제 Controller Test")
	@Test
	void deleteArticle_Succeeds() throws Exception {

		mockMvc.perform(
				RestDocumentationRequestBuilders.delete("/api/articles/{slug}", ARTICLE1.getSlug().getValue())
					.contentType(MediaType.APPLICATION_JSON)
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
			)
			.andExpect(status().isNoContent())
			.andDo(restDocs.document(
				pathParameters(
					parameterWithName("slug").description("게시물 슬러그")
				),
				requestHeaders(
					headerWithName(AUTHORIZATION_HEADER_NAME).description("토큰")
				)
			));

		then(deleteArticleUseCase).should()
			.deleteArticle(eq(ARTICLE1.getSlug().getValue()), eq(USER1.getId().getValue()));
	}

}

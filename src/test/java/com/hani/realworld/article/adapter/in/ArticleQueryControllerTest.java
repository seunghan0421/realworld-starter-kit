package com.hani.realworld.article.adapter.in;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.hani.realworld.article.application.port.in.command.PagingParam;
import com.hani.realworld.article.application.port.in.GetArticleListQuery;
import com.hani.realworld.article.application.port.in.GetArticleQuery;
import com.hani.realworld.article.application.port.in.GetFeedArticleListQuery;
import com.hani.realworld.article.application.port.in.command.GetListArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.common.descriptor.ArticleFieldDescriptor;
import com.hani.realworld.common.descriptor.ProfileFieldDescriptor;
import com.hani.realworld.common.restdocs.ControllerTest;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

@WebMvcTest(ArticleQueryController.class)
class ArticleQueryControllerTest extends ControllerTest {

	@MockBean
	private GetArticleQuery getArticleQuery;

	@MockBean
	private GetArticleListQuery getArticleListQuery;

	@MockBean
	private GetFeedArticleListQuery getFeedArticleListQuery;

	@Test
	void getArticle_Succeeds() throws Exception {
		// given
		ArticleResult response =
			ArticleResult.of(ARTICLE1, ProfileResult.of(ARTICLE1.getAuthor(), false), false, 2);

		Optional<Long> userId = Optional.of(ARTICLE1.getAuthor().getId().getValue());
		given(getArticleQuery.getArticle(eq(ARTICLE1.getSlug().getSlug()), eq(userId)))
			.willReturn(response);

		// when
		ResultActions result = mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/articles/{slug}", ARTICLE1.getSlug().getSlug())
					.contentType(MediaType.APPLICATION_JSON)
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
			)
			.andExpect(status().isOk());

		// then
		result.andDo(
				restDocs.document(
					pathParameters(
						parameterWithName("slug").description("게시물 슬러그")
					),
					responseFields(
						fieldWithPath("article").type(JsonFieldType.OBJECT).description("기사 정보")
					).andWithPrefix("article.", ArticleFieldDescriptor.article)
						.andWithPrefix("article.author.", ProfileFieldDescriptor.profile)
				)
			);

		then(getArticleQuery).should()
			.getArticle(eq(ARTICLE1.getSlug().getSlug()), eq(userId));
	}

	@Test
	void getMultipleArticleList_Succeeds() throws Exception {
		// given
		GetListArticleCommand command =
			new GetListArticleCommand(PAGING_PARAM, "tag1", "author1", "favorited1");
		List<ArticleResult> response = List.of(
			ArticleResult.of(ARTICLE1, ProfileResult.of(PROFILE1, false), true, 1),
			ArticleResult.of(ARTICLE2, ProfileResult.of(PROFILE2, true), false, 0)
		);

		Optional<Long> userId = Optional.of(USER1.getId().getValue());
		given(getArticleListQuery.getArticleList(eq(command), eq(userId)))
			.willReturn(response);

		// when
		ResultActions result = mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/articles")
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
					.queryParam("offset", "0")
					.queryParam("limit", "20")
					.queryParam("tag", "tag1")
					.queryParam("author", "author1")
					.queryParam("favorited", "favorited1")
			)
			.andExpect(status().isOk());

		// then
		result.andDo(
			restDocs.document(
				requestParameters(
					parameterWithName("offset").description("게시물 오프셋"),
					parameterWithName("limit").description("게시물 개수"),
					parameterWithName("tag").description("태그"),
					parameterWithName("author").description("작성자"),
					parameterWithName("favorited").description("즐겨찾기 여부")
				),
				responseFields(
					fieldWithPath("articles").type(JsonFieldType.ARRAY).description("게시물 리스트")
				).andWithPrefix("articles.[].", ArticleFieldDescriptor.article)
					.andWithPrefix("articles.[].author.", ProfileFieldDescriptor.profile)
			)
		);

		then(getArticleListQuery).should()
			.getArticleList(eq(command), eq(userId));
	}

	@Test
	void getFeedArticleList_Succeeds() throws Exception {
		// given
		List<ArticleResult> response = List.of(
			ArticleResult.of(ARTICLE1, ProfileResult.of(PROFILE1, false), true, 1),
			ArticleResult.of(ARTICLE2, ProfileResult.of(PROFILE2, true), false, 0)
		);

		given(getFeedArticleListQuery.getFeedArticleList(eq(PAGING_PARAM), eq(USER1.getId().getValue())))
			.willReturn(response);

		// when
		ResultActions result = mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/articles/feed")
					.contentType(MediaType.APPLICATION_JSON)
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
					.queryParam("offset", "0")
					.queryParam("limit", "20")
			)
			.andExpect(status().isOk());

		// then
		result.andDo(
			restDocs.document(
				requestHeaders(
					headerWithName(AUTHORIZATION_HEADER_NAME).description("토큰")
				),
				requestParameters(
					parameterWithName("offset").description("게시물 오프셋"),
					parameterWithName("limit").description("게시물 개수")
				),
				responseFields(
					fieldWithPath("articles").type(JsonFieldType.ARRAY).description("게시물 리스트")
				).andWithPrefix("articles.[].", ArticleFieldDescriptor.article)
					.andWithPrefix("articles.[].author.", ProfileFieldDescriptor.profile)
			)
		);

		then(getFeedArticleListQuery).should()
			.getFeedArticleList(eq(PAGING_PARAM), eq(USER1.getId().getValue()));
	}

	private PagingParam PAGING_PARAM = new PagingParam(0, 20);

}

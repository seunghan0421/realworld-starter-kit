package com.hani.realworld.comment.adapter.in;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.CommentFixture.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
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

import com.hani.realworld.comment.adapter.in.dto.AddCommentRequest;
import com.hani.realworld.comment.application.port.in.AddCommentUseCase;
import com.hani.realworld.comment.application.port.in.DeleteCommentUseCase;
import com.hani.realworld.comment.application.port.in.GetCommentsQuery;
import com.hani.realworld.comment.application.port.in.command.AddCommentCommand;
import com.hani.realworld.comment.application.port.in.result.CommentResult;
import com.hani.realworld.common.descriptor.CommentFieldDescriptor;
import com.hani.realworld.common.descriptor.ProfileFieldDescriptor;
import com.hani.realworld.common.restdocs.ControllerTest;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

@WebMvcTest(CommentController.class)
class CommentControllerTest extends ControllerTest {

	@MockBean
	private AddCommentUseCase addCommentUseCase;

	@MockBean
	private DeleteCommentUseCase deleteCommentUseCase;

	@MockBean
	private GetCommentsQuery getCommentsQuery;

	@Test
	void AddCommentToArticle_Succeeds() throws Exception {
		String request = createJson(new AddCommentRequest(COMMENT1.getBody()));
		final CommentResult response = CommentResult.of(COMMENT1, ProfileResult.of(PROFILE1, false));

		given(addCommentUseCase.addComment(
			any(AddCommentCommand.class),
			eq(ARTICLE1.getSlug().getSlug()),
			eq(USER1.getId().getValue())))
			.willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/articles/{slug}/comments", ARTICLE1.getSlug().getSlug())
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
					pathParameters(
						parameterWithName("slug").description("게시물 슬러그")
					),
					requestFields(
						fieldWithPath("comment.body").type(JsonFieldType.STRING).description("게시물 댓글")
					),
					responseFields(
						fieldWithPath("comment").type(JsonFieldType.OBJECT).description("댓글 정보")
					).andWithPrefix("comment.", CommentFieldDescriptor.comment)
						.andWithPrefix("comment.author.", ProfileFieldDescriptor.profile)
				)
			);

		then(addCommentUseCase).should()
			.addComment(
				eq(new AddCommentCommand(COMMENT1.getBody())),
				eq(ARTICLE1.getSlug().getSlug()),
				eq(USER1.getId().getValue()));
	}

	@Test
	void getMultipleCommentOfArticle_Succeeds() throws Exception {
		List<CommentResult> response = List.of(
			CommentResult.of(COMMENT1, ProfileResult.of(PROFILE1, false)),
			CommentResult.of(COMMENT2, ProfileResult.of(PROFILE2, true))
		);

		Optional<Long> userId = Optional.of(USER1.getId().getValue());
		given(getCommentsQuery.getComments(eq(ARTICLE1.getSlug().getSlug()), eq(userId)))
			.willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/articles/{slug}/comments", ARTICLE1.getSlug().getSlug())
					.contentType(MediaType.APPLICATION_JSON)
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					pathParameters(
						parameterWithName("slug").description("게시물 슬러그")
					),
					responseFields(
						fieldWithPath("comments").type(JsonFieldType.ARRAY).description("댓글 리스트")
					).andWithPrefix("comments.[].", CommentFieldDescriptor.comment)
						.andWithPrefix("comments.[].author.", ProfileFieldDescriptor.profile)
				)
			);

		then(getCommentsQuery).should()
			.getComments(eq(ARTICLE1.getSlug().getSlug()), eq(userId));
	}

	@Test
	void deleteCommentFromArticle_Succeeds() throws Exception {

		mockMvc.perform(
				RestDocumentationRequestBuilders.
					delete("/api/articles/{slug}/comments/{id}", ARTICLE1.getSlug().getSlug(), COMMENT1.getId().getValue())
					.contentType(MediaType.APPLICATION_JSON)
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
			)
			.andExpect(status().isNoContent())
			.andDo(restDocs.document(
				pathParameters(
					parameterWithName("slug").description("게시물 슬러그"),
					parameterWithName("id").description("댓글 식별번호")
				),
				requestHeaders(
					headerWithName(AUTHORIZATION_HEADER_NAME).description("토큰")
				)
			));

		then(deleteCommentUseCase).should()
			.deleteComment(eq(COMMENT1.getId().getValue()), eq(USER1.getId().getValue()));
	}

}

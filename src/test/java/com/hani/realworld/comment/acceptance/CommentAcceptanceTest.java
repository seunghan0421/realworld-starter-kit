package com.hani.realworld.comment.acceptance;

import static com.hani.realworld.comment.adapter.in.dto.response.CommentResponse.*;
import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;
import static org.springframework.test.annotation.DirtiesContext.*;

import java.util.List;
import java.util.stream.Stream;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hani.realworld.AcceptanceTest;
import com.hani.realworld.comment.adapter.in.dto.request.AddCommentRequest;
import com.hani.realworld.comment.adapter.in.dto.response.CommentResponse;
import com.hani.realworld.comment.adapter.in.dto.response.MultipleCommentResponse;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CommentAcceptanceTest extends AcceptanceTest {

	private final String uri = "/api/articles/" + ARTICLE1.getSlug().getSlug() + "/comments";

	@DisplayName("댓글 기능 Acceptance Test")
	@TestFactory
	Stream<DynamicTest> manageComment() throws JsonProcessingException {

		createArticle(ARTICLE1);

		return Stream.of(
			dynamicTest("댓글 생성", () -> {
				CommentResponse response = createComment("comment1");
				CommentInfo comment = response.getComment();
				assertThat(comment.getAuthor().getUsername()).isEqualTo(USER1.getUsername());
				assertThat(comment.getBody()).isEqualTo("comment1");
			}),
			dynamicTest("게시글에 달린 모든 댓글 조회", () -> {
				createComment("comment2");
				createComment("comment3");
				MultipleCommentResponse response = get(uri, HttpStatus.SC_OK,
					MultipleCommentResponse.class);
				List<CommentInfo> comments = response.getComments();
				assertThat(comments).hasSize(3);
			}),
			dynamicTest("댓글 삭제", () -> {
				delete(uri + "/3", token);
			})
		);
	}

	private CommentResponse createComment(String commentBody) throws JsonProcessingException {
		AddCommentRequest request = new AddCommentRequest(commentBody);
		String body = objectMapper.writeValueAsString(request);
		return post(uri, body, token, HttpStatus.SC_CREATED, CommentResponse.class);
	}
}

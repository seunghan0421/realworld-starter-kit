package com.hani.realworld.comment.domain;

import static com.hani.realworld.common.fixture.CommentFixture.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hani.realworld.common.exception.NotMyCommentException;

class CommentTest {

	@DisplayName("댓글 팩토리 도메인 테스트 - 성공, Comment.without()")
	@Test
	void comment_withoutId_test_Succeeds() {
		// when, then
		assertDoesNotThrow(() -> Comment.withoutId(
			COMMENT1.getArticleId(),
			COMMENT1.getAuthor(),
			COMMENT1.getBody()));
	}

	@DisplayName("댓글 팩토리 도메인 테스트 - 성공, Comment.with()")
	@Test
	void comment_withId_Succeeds() {
		// when, then
		assertDoesNotThrow(() -> Comment.withId(
			COMMENT1.getId(),
			COMMENT1.getArticleId(),
			COMMENT1.getAuthor(),
			COMMENT1.getBody(),
			COMMENT1.getCreatedAt(),
			COMMENT1.getUpdatedAt()));
	}

	@DisplayName("자기 댓글 확인 도메인 테스트 - 성공, comment.checkIsMyComment()")
	@Test
	void checkIsMyComment_Succeeds() {
		// given
		Comment comment = defaultComment().withAuthor(PROFILE1).build();

		// when, then
		assertDoesNotThrow(() -> comment.checkIsMyComment(USER1.getId().getValue()));
	}

	@DisplayName("자기 댓글 확인 도메인 테스트 - 실패,  comment.checkIsMyComment(), throw NotMyCommentException")
	@Test
	void checkIsMyComment_Failure() {
		// given
		Comment comment = defaultComment().withAuthor(PROFILE1).build();

		// when, then
		assertThrows(NotMyCommentException.class, () -> comment.checkIsMyComment(USER2.getId().getValue()));
	}
}

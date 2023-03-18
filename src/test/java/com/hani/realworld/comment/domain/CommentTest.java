package com.hani.realworld.comment.domain;

import static com.hani.realworld.common.fixture.CommentFixture.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hani.realworld.common.exception.NotMyCommentException;

class CommentTest {

	@Test
	void comment_withoutId_test_Succeeds() {
		// when, then
		assertDoesNotThrow(() -> Comment.withoutId(
			COMMENT1.getArticleId(),
			COMMENT1.getAuthor(),
			COMMENT1.getBody()));
	}

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

	@Test
	void checkIsMyComment_Succeeds() {
		// given
		Comment comment = defaultComment().withAuthor(PROFILE1).build();

		// when, then
		assertDoesNotThrow(() -> comment.checkIsMyComment(USER1.getId().getValue()));
	}

	@Test
	void checkIsMyComment_Failure() {
		// given
		Comment comment = defaultComment().withAuthor(PROFILE1).build();

		// when, then
		assertThrows(NotMyCommentException.class, () -> comment.checkIsMyComment(USER2.getId().getValue()));
	}
}

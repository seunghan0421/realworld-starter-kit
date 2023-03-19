package com.hani.realworld.comment.application.service;

import static com.hani.realworld.common.fixture.CommentFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.comment.application.port.out.DeleteCommentWithCommentIdPort;
import com.hani.realworld.comment.application.port.out.GetCommentWithIdPort;
import com.hani.realworld.comment.domain.Comment;

class DeleteCommentServiceTest {

	private final GetCommentWithIdPort getCommentWithIdPort =
		Mockito.mock(GetCommentWithIdPort.class);

	private final DeleteCommentWithCommentIdPort deleteCommentWithCommentIdPort =
		Mockito.mock(DeleteCommentWithCommentIdPort.class);

	private final DeleteCommentService deleteCommentService =
		new DeleteCommentService(getCommentWithIdPort, deleteCommentWithCommentIdPort);

	@DisplayName("댓글 삭제 서비스 테스트 - 성공")
	@Test
	void deleteArticle_Succeeds() {
		// given
		Comment comment = getMockComment1();

		given(getCommentWithIdPort.getComment(eq(COMMENT1.getId()))).willReturn(comment);

		// when
		deleteCommentService.deleteComment(COMMENT1.getId().getValue(), USER1.getId().getValue());

		// then
		then(getCommentWithIdPort).should().getComment(eq(COMMENT1.getId()));
		then(comment).should().checkIsMyComment(eq(USER1.getId().getValue()));
		then(deleteCommentWithCommentIdPort).should().deleteComment(eq(COMMENT1.getId()));
	}
}

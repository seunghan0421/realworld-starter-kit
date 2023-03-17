package com.hani.realworld.comment.application.service;

import static com.hani.realworld.comment.domain.Comment.*;

import javax.transaction.Transactional;

import com.hani.realworld.comment.application.port.in.DeleteCommentUseCase;
import com.hani.realworld.comment.application.port.out.DeleteCommentWithCommentIdPort;
import com.hani.realworld.comment.application.port.out.GetCommentWithIdPort;
import com.hani.realworld.comment.domain.Comment;
import com.hani.realworld.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class DeleteCommentService implements DeleteCommentUseCase {

	private final GetCommentWithIdPort getCommentWithIdPort;
	private final DeleteCommentWithCommentIdPort deleteCommentWithCommentIdPort;

	@Override
	public void deleteComment(Long commentId, Long userId) {
		Comment comment = getCommentWithIdPort.getComment(new CommentId(commentId));

		comment.checkIsMyComment(userId);

		deleteCommentWithCommentIdPort.deleteComment(new CommentId(commentId));
	}
}

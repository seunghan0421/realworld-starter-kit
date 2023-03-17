package com.hani.realworld.comment.application.service;

import javax.transaction.Transactional;

import com.hani.realworld.comment.application.port.in.DeleteCommentUseCase;
import com.hani.realworld.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class DeleteCommentService implements DeleteCommentUseCase {

	@Override
	public void deleteComment(String slug, Long commentId, Long id) {

	}
}

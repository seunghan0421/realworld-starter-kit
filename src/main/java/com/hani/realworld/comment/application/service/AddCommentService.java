package com.hani.realworld.comment.application.service;

import javax.transaction.Transactional;

import com.hani.realworld.comment.application.port.in.AddCommentUseCase;
import com.hani.realworld.comment.application.port.in.command.AddCommentCommand;
import com.hani.realworld.comment.application.port.in.result.CommentResult;
import com.hani.realworld.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class AddCommentService implements AddCommentUseCase {
	@Override
	public CommentResult addComment(AddCommentCommand command, String slug, Long id) {
		return null;
	}
}

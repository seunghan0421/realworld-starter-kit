package com.hani.realworld.comment.application.port.in;

import javax.validation.constraints.NotNull;

import com.hani.realworld.comment.application.port.in.command.AddCommentCommand;
import com.hani.realworld.comment.application.port.in.result.CommentResult;

public interface AddCommentUseCase {
	CommentResult addComment(@NotNull AddCommentCommand command, @NotNull String slug, @NotNull Long id);
}

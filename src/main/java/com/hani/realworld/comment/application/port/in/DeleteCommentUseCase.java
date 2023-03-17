package com.hani.realworld.comment.application.port.in;

import javax.validation.constraints.NotNull;

public interface DeleteCommentUseCase {
	void deleteComment(@NotNull Long commentId, @NotNull Long id);
}

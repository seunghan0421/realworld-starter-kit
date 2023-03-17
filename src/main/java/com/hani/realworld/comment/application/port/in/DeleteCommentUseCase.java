package com.hani.realworld.comment.application.port.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface DeleteCommentUseCase {
	void deleteComment(@NotBlank String slug, @NotNull Long commentId, @NotNull Long id);
}

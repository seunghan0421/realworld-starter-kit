package com.hani.realworld.comment.application.port.in;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hani.realworld.comment.application.port.in.result.CommentResult;

public interface GetCommentQuery {
	List<CommentResult> getComments(@NotBlank String slug, @NotNull Optional<Long> id);
}

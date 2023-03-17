package com.hani.realworld.comment.application.port.out;

import static com.hani.realworld.comment.domain.Comment.*;

public interface DeleteCommentWithCommentIdPort {
	void deleteComment(CommentId commentId);
}

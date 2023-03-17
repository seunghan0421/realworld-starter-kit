package com.hani.realworld.comment.application.port.out;

import static com.hani.realworld.comment.domain.Comment.*;

import com.hani.realworld.comment.domain.Comment;

public interface GetCommentWithIdPort {
	Comment getComment(CommentId commentId);
}

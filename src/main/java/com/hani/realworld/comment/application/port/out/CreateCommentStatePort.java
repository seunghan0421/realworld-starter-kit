package com.hani.realworld.comment.application.port.out;

import com.hani.realworld.comment.domain.Comment;

public interface CreateCommentStatePort {
	Comment createComment(Comment comment);
}

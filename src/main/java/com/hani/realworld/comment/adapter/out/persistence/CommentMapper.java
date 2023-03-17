package com.hani.realworld.comment.adapter.out.persistence;

import static com.hani.realworld.comment.domain.Comment.*;

import org.springframework.stereotype.Component;

import com.hani.realworld.comment.domain.Comment;
import com.hani.realworld.user.domain.Profile;

@Component
public class CommentMapper {

	CommentJpaEntity mapToCommentJpaEntity(Comment comment) {
		return new CommentJpaEntity(
			comment.getId() == null ? null : comment.getId().getValue(),
			comment.getArticleId(),
			comment.getAuthor().getId().getValue(),
			comment.getBody(),
			comment.getCreatedAt(),
			comment.getUpdatedAt());
	}

	Comment mapToCommentEntity(CommentJpaEntity commentJpaEntity, Profile author) {
		return withId(
			new CommentId(commentJpaEntity.getId()),
			commentJpaEntity.getArticleId(),
			author,
			commentJpaEntity.getBody(),
			commentJpaEntity.getCreatedAt(),
			commentJpaEntity.getUpdatedAt());
	}
}

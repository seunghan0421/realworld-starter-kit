package com.hani.realworld.comment.domain;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import com.hani.realworld.common.exception.comment.NotMyCommentException;
import com.hani.realworld.user.domain.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class Comment {

	/* Comment Identification number */
	private final CommentId id;

	/* Article that comment is added */
	private final Long articleId;

	/* Profile who write the Comment */
	private final Profile author;

	/* content of Comment */
	private final String body;

	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	/**
	 * Creates an {@link Comment} entity without an ID. Use to create a new entity that is not yet
	 * persisted.
	 */
	public static Comment withoutId(Long articleId, Profile author, String body) {
		return new Comment(null, articleId, author, body, LocalDateTime.now(), LocalDateTime.now());
	}

	/**
	 * Creates an {@link Comment} entity with an ID. Use to reconstitute a persisted entity.
	 */
	public static Comment withId(
		CommentId commentId,
		Long articleId,
		Profile author,
		String body,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {

		return new Comment(commentId, articleId, author, body, createdAt, updatedAt);
	}

	public void checkIsMyComment(long userId) {
		if (this.author.getUser().getId().getValue() != userId) {
			throw new NotMyCommentException();
		}
	}

	@Value
	public static class CommentId {
		private Long value;
	}
}

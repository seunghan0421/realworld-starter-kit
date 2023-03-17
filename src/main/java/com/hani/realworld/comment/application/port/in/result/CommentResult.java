package com.hani.realworld.comment.application.port.in.result;

import java.time.LocalDateTime;

import com.hani.realworld.comment.domain.Comment;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentResult {
	private final Long id;
	private final ProfileResult author;
	private final String body;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public static CommentResult of(Comment comment, ProfileResult profileResult) {
		return new CommentResult(
			comment.getId().getValue(),
			profileResult,
			comment.getBody(),
			comment.getCreatedAt(),
			comment.getUpdatedAt());
	}
}

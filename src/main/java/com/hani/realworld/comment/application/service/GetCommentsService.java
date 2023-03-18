package com.hani.realworld.comment.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.comment.application.port.in.GetCommentsQuery;
import com.hani.realworld.comment.application.port.in.result.CommentResult;
import com.hani.realworld.comment.application.port.out.LoadMultipleCommentWithArticleIdPort;
import com.hani.realworld.comment.domain.Comment;
import com.hani.realworld.common.annotation.Query;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetCommentsService implements GetCommentsQuery {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort;
	private final LoadMultipleCommentWithArticleIdPort loadMultipleCommentWithArticleIdPort;

	private final GetProfileQuery getProfileQuery;

	@Override
	public List<CommentResult> getComments(String slug, Optional<Long> userId) {
		Article article = loadArticleWithSlugPort.load(slug);

		List<Comment> comments = loadMultipleCommentWithArticleIdPort.getCommentsWithArticleId(article.getId());

		return comments.stream()
			.map(comment -> {
				ProfileResult profileResult =
					getProfileQuery.getProfile(comment.getAuthor().getUser().getUsername(), userId);

				return CommentResult.of(comment, profileResult);
			})
			.collect(Collectors.toList());
	}
}

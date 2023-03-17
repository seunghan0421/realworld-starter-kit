package com.hani.realworld.comment.application.service;

import java.util.List;
import java.util.Optional;

import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.comment.application.port.in.GetCommentQuery;
import com.hani.realworld.comment.application.port.in.result.CommentResult;
import com.hani.realworld.common.annotation.Query;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetCommentService implements GetCommentQuery {
	@Override
	public List<CommentResult> getComments(String slug, Optional<Long> id) {
		return null;
	}
}

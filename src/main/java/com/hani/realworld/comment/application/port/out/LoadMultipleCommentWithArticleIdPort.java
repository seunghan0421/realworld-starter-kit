package com.hani.realworld.comment.application.port.out;

import static com.hani.realworld.article.domain.Article.*;

import java.util.List;

import com.hani.realworld.comment.domain.Comment;

public interface LoadMultipleCommentWithArticleIdPort {
	List<Comment> getCommentsWithArticleId(ArticleId articleId);
}

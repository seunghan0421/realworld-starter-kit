package com.hani.realworld.article.application.port.out;

import static com.hani.realworld.article.domain.Article.*;

import com.hani.realworld.article.domain.Article;

public interface DeleteArticleWithArticleId {
	void delete(ArticleId articleId);
}

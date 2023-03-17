package com.hani.realworld.article.application.port.out;

import static com.hani.realworld.article.domain.Article.*;

public interface DeleteArticleWithArticleIdPort {
	void delete(ArticleId articleId);
}

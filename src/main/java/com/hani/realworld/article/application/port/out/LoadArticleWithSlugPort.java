package com.hani.realworld.article.application.port.out;

import com.hani.realworld.article.domain.Article;

public interface LoadArticleWithSlugPort {
	Article load(String slug);
}

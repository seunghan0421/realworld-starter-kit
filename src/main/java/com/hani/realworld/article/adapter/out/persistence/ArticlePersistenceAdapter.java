package com.hani.realworld.article.adapter.out.persistence;

import com.hani.realworld.article.application.port.out.DeleteArticleWithArticleId;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.application.port.out.UpdateArticleStatePort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.common.annotation.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ArticlePersistenceAdapter implements
	LoadArticleWithSlugPort,
	UpdateArticleStatePort,
	DeleteArticleWithArticleId {

	@Override
	public void delete(Article.ArticleId articleId) {

	}

	@Override
	public Article load(String slug) {
		return null;
	}

	@Override
	public void update(Article article) {

	}
}

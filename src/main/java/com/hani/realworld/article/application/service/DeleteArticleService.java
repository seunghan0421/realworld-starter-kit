package com.hani.realworld.article.application.service;

import static com.hani.realworld.user.domain.User.*;

import javax.transaction.Transactional;

import com.hani.realworld.article.application.port.in.DeleteArticleUseCase;
import com.hani.realworld.article.application.port.out.DeleteArticleWithArticleIdPort;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class DeleteArticleService implements DeleteArticleUseCase {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort;
	private final DeleteArticleWithArticleIdPort deleteArticleWithArticleIdPort;

	@Override
	public void deleteArticle(String slug, Long userId) {
		Article article = loadArticleWithSlugPort.load(slug);

		article.checkisMyArticle(new UserId(userId));

		deleteArticleWithArticleIdPort.delete(article.getId());
	}
}

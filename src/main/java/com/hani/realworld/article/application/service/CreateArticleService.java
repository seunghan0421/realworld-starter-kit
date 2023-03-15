package com.hani.realworld.article.application.service;

import static com.hani.realworld.user.domain.User.*;

import javax.transaction.Transactional;

import com.hani.realworld.article.application.port.in.CreateArticleUseCase;
import com.hani.realworld.article.application.port.in.command.CreateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.CreateArticleStatePort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;
import com.hani.realworld.user.domain.Profile;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class CreateArticleService implements CreateArticleUseCase {

	private final CreateArticleStatePort createArticleStatePort;
	private final LoadProfileWithUserIdPort loadProfileWithUserIdPort;

	@Override
	public ArticleResult create(CreateArticleCommand command, Long userId) {
		Profile author = loadProfileWithUserIdPort.loadProfileWithUserId(new UserId(userId));

		Article article = Article.withoutId(
			author,
			command.getTags(),
			command.getTitle(),
			command.getDescription(),
			command.getBody());

		createArticleStatePort.create(article);

		return ArticleResult.of(article, ProfileResult.of(author, false));
	}
}

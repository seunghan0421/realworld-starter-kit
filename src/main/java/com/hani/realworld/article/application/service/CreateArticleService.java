package com.hani.realworld.article.application.service;

import javax.transaction.Transactional;

import com.hani.realworld.article.application.port.in.CreateArticleUseCase;
import com.hani.realworld.article.application.port.in.command.CreateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class CreateArticleService implements CreateArticleUseCase {

	@Override
	public ArticleResult create(CreateArticleCommand command, Long userId) {
		return null;
	}
}

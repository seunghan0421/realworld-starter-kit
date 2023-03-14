package com.hani.realworld.article.application.service;

import javax.transaction.Transactional;

import com.hani.realworld.article.application.port.in.UpdateArticleUseCase;
import com.hani.realworld.article.application.port.in.command.UpdateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class UpdateArticleService implements UpdateArticleUseCase {

	@Override
	public ArticleResult update(UpdateArticleCommand command, Long userId) {
		return null;
	}
}

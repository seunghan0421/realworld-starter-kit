package com.hani.realworld.article.application.service;

import javax.transaction.Transactional;

import com.hani.realworld.article.application.port.in.DeleteArticleUseCase;
import com.hani.realworld.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class DeleteArticleService implements DeleteArticleUseCase {
	@Override
	public void delete(String slug, Long userId) {

	}
}

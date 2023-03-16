package com.hani.realworld.article.application.service;

import java.util.Set;

import com.hani.realworld.article.application.port.in.GetAllTagsUseCase;
import com.hani.realworld.article.application.port.out.GetAllTagsPort;
import com.hani.realworld.common.annotation.Query;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetAllTagsService implements GetAllTagsUseCase {

	private final GetAllTagsPort getAllTagsPort;

	@Override
	public Set<String> getAllTags() {
		return getAllTagsPort.getAllTags();
	}
}

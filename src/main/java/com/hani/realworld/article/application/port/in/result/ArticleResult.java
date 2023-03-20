package com.hani.realworld.article.application.port.in.result;

import java.time.LocalDateTime;
import java.util.Set;

import com.hani.realworld.article.domain.Article;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArticleResult {
	private final String slug;
	private final String title;
	private final String description;
	private final String body;
	private final Set<String> tagList;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	private final boolean favorited;
	private final int favoritesCount;
	private final ProfileResult profileResult;

	public static ArticleResult of(
		Article article,
		ProfileResult profileResult,
		boolean favorited,
		int favoritesCount) {
		return new ArticleResult(
			article.getSlug().getValue(),
			article.getTitle(),
			article.getDescription(),
			article.getBody(),
			article.getTags().getTagSet(),
			article.getCreatedAt(),
			article.getUpdatedAt(),
			favorited,
			favoritesCount,
			profileResult);
	}
}

package com.hani.realworld.article.adapter.in.dto.response;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.user.adapter.in.web.dto.response.ProfileResponse.ProfileInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleResponse {

	private ArticleInfo article;

	public static ArticleResponse of(ArticleResult result) {
		return new ArticleResponse(ArticleInfo.of(result));
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class ArticleInfo {
		private String slug;
		private String title;
		private String description;
		private String body;
		private List<String> tagList;
		@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
		private LocalDateTime createdAt;
		@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
		private LocalDateTime updatedAt;
		private boolean favorited;
		private int favoritesCount;
		private ProfileInfo author;

		public static ArticleInfo of(ArticleResult result) {
			return new ArticleInfo(
				result.getSlug(),
				result.getTitle(),
				result.getDescription(),
				result.getBody(),
				new ArrayList<>(result.getTagList()),
				result.getCreatedAt(),
				result.getUpdatedAt(),
				result.isFavorited(),
				result.getFavoritesCount(),
				ProfileInfo.of(result.getProfileResult()));
		}
	}

}

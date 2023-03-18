package com.hani.realworld.article.adapter.in.dto.response;

import static com.hani.realworld.article.adapter.in.dto.response.ArticleResponse.*;

import java.util.List;
import java.util.stream.Collectors;

import com.hani.realworld.article.application.port.in.result.ArticleResult;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MultipleArticleResponse {

	private List<ArticleInfo> articles;

	public static MultipleArticleResponse of(List<ArticleResult> articleResults) {
		return new MultipleArticleResponse(articleResults.stream()
			.map(ArticleInfo::of)
			.collect(Collectors.toList()));
	}
}

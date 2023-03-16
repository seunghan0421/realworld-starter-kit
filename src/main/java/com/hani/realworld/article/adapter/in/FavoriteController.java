package com.hani.realworld.article.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.article.adapter.in.dto.ArticleResponse;
import com.hani.realworld.article.application.port.in.FavoriteArticleUseCase;
import com.hani.realworld.article.application.port.in.UnFavoriteArticleUseCase;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.infra.jwt.LoginToken;
import com.hani.realworld.infra.jwt.LoginUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class FavoriteController {

	private final FavoriteArticleUseCase favoriteArticleUseCase;
	private final UnFavoriteArticleUseCase unFavoriteArticleUseCase;

	@PostMapping("/{slug}/favorite")
	ResponseEntity<ArticleResponse> favoriteArticle(
		@PathVariable("slug") String slug,
		@LoginUser LoginToken token) {

		ArticleResult articleResult = favoriteArticleUseCase.favoriteArticle(slug, token.getId());

		return ResponseEntity.ok(ArticleResponse.of(articleResult));
	}

	@DeleteMapping("/{slug}/favorite")
	ResponseEntity<ArticleResponse> unFavoriteArticle(
		@PathVariable("slug") String slug,
		@LoginUser LoginToken token) {

		ArticleResult articleResult = unFavoriteArticleUseCase.unFavoriteArticle(slug, token.getId());

		return ResponseEntity.ok(ArticleResponse.of(articleResult));
	}
}

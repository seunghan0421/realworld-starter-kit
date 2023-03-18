package com.hani.realworld.article.adapter.in;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.article.adapter.in.dto.response.ArticleResponse;
import com.hani.realworld.article.adapter.in.dto.request.CreateArticleRequest;
import com.hani.realworld.article.adapter.in.dto.request.UpdateArticleRequest;
import com.hani.realworld.article.application.port.in.CreateArticleUseCase;
import com.hani.realworld.article.application.port.in.DeleteArticleUseCase;
import com.hani.realworld.article.application.port.in.UpdateArticleUseCase;
import com.hani.realworld.article.application.port.in.command.CreateArticleCommand;
import com.hani.realworld.article.application.port.in.command.UpdateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.infra.jwt.LoginToken;
import com.hani.realworld.infra.jwt.LoginUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class ArticleController {

	private final CreateArticleUseCase createArticleUseCase;
	private final UpdateArticleUseCase updateArticleUseCase;
	private final DeleteArticleUseCase deleteArticleUseCase;

	@PostMapping
	ResponseEntity<ArticleResponse> createArticle(
		@RequestBody CreateArticleRequest request,
		@LoginUser LoginToken loginToken) {

		CreateArticleCommand command = new CreateArticleCommand(
			request.getTitle(),
			request.getDescription(),
			request.getBody(),
			request.getTagList());

		ArticleResult articleResult = createArticleUseCase.createArticle(command, loginToken.getId());

		return ResponseEntity.created(URI.create("/api/articles/" + articleResult.getSlug()))
			.body(ArticleResponse.of(articleResult));
	}

	@PutMapping("/{slug}")
	ResponseEntity<ArticleResponse> updateArticle(
		@PathVariable("slug") String slug,
		@RequestBody UpdateArticleRequest request,
		@LoginUser LoginToken loginToken) {

		UpdateArticleCommand command = new UpdateArticleCommand(
			request.getTitle(),
			request.getDescription(),
			request.getBody());

		ArticleResult articleResult = updateArticleUseCase.updateArticle(command, slug, loginToken.getId());

		return ResponseEntity.ok(ArticleResponse.of(articleResult));
	}

	@DeleteMapping("/{slug}")
	ResponseEntity<Void> deleteArticle(
		@PathVariable("slug") String slug,
		@LoginUser LoginToken loginToken) {

		deleteArticleUseCase.deleteArticle(slug, loginToken.getId());

		return ResponseEntity.noContent().build();
	}
}

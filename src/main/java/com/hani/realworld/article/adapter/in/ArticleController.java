package com.hani.realworld.article.adapter.in;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.article.adapter.in.dto.ArticleResponse;
import com.hani.realworld.article.adapter.in.dto.CreateArticleRequest;
import com.hani.realworld.article.adapter.in.dto.UpdateArticleRequest;
import com.hani.realworld.article.application.port.in.CreateArticleUseCase;
import com.hani.realworld.article.application.port.in.DeleteArticleUseCase;
import com.hani.realworld.article.application.port.in.GetArticleQuery;
import com.hani.realworld.article.application.port.in.UpdateArticleUseCase;
import com.hani.realworld.article.application.port.in.command.CreateArticleCommand;
import com.hani.realworld.article.application.port.in.command.UpdateArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.infra.jwt.LoginToken;
import com.hani.realworld.infra.jwt.LoginUser;
import com.hani.realworld.infra.jwt.OptionalUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class ArticleController {

	private final CreateArticleUseCase createArticleUseCase;
	private final UpdateArticleUseCase updateArticleUseCase;
	private final DeleteArticleUseCase deleteArticleUseCase;

	private final GetArticleQuery getArticleQuery;

	@PostMapping
	ResponseEntity<ArticleResponse> createArticle(
		@RequestBody CreateArticleRequest request,
		@LoginUser LoginToken loginToken) {

		CreateArticleCommand command = new CreateArticleCommand(
			request.getTitle(),
			request.getDescription(),
			request.getBody(),
			request.getTagList());

		ArticleResult articleResult = createArticleUseCase.create(command, loginToken.getId());

		return ResponseEntity.created(URI.create("/api/articles/" + articleResult.getSlug()))
			.body(ArticleResponse.of(articleResult));
	}

	@GetMapping("/{slug}")
	ResponseEntity<ArticleResponse> getArticle(
		@PathVariable("slug") String slug,
		@OptionalUser LoginToken loginToken) {

		Optional<Long> userId = Optional.ofNullable(loginToken)
			.map(LoginToken::getId);

		ArticleResult articleResult = getArticleQuery.getArticle(slug, userId);

		return ResponseEntity.ok(ArticleResponse.of(articleResult));
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

		ArticleResult articleResult = updateArticleUseCase.update(command, slug, loginToken.getId());

		return ResponseEntity.ok(ArticleResponse.of(articleResult));
	}

	@DeleteMapping("/{slug}")
	ResponseEntity<Void> deleteArticle(
		@PathVariable("slug") String slug,
		@LoginUser LoginToken loginToken) {

		deleteArticleUseCase.delete(slug, loginToken.getId());

		return ResponseEntity.noContent().build();
	}
}

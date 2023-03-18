package com.hani.realworld.article.adapter.in;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.article.application.port.in.command.PagingParam;
import com.hani.realworld.article.adapter.in.dto.response.ArticleResponse;
import com.hani.realworld.article.adapter.in.dto.response.MultipleArticleResponse;
import com.hani.realworld.article.application.port.in.GetArticleQuery;
import com.hani.realworld.article.application.port.in.GetArticleListQuery;
import com.hani.realworld.article.application.port.in.GetFeedArticleListQuery;
import com.hani.realworld.article.application.port.in.command.GetListArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.infra.jwt.LoginToken;
import com.hani.realworld.infra.jwt.LoginUser;
import com.hani.realworld.infra.jwt.OptionalUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class ArticleQueryController {

	private final GetArticleQuery getArticleQuery;
	private final GetArticleListQuery getArticleListQuery;
	private final GetFeedArticleListQuery getFeedArticleListQuery;

	// Article 단건 조회
	@GetMapping("/{slug}")
	ResponseEntity<ArticleResponse> getArticle(
		@PathVariable("slug") String slug,
		@OptionalUser LoginToken loginToken) {

		Optional<Long> userId = Optional.ofNullable(loginToken)
			.map(LoginToken::getId);

		ArticleResult articleResult = getArticleQuery.getArticle(slug, userId);

		return ResponseEntity.ok(ArticleResponse.of(articleResult));
	}

	/**
	 * Article 리스트 조회
	 * Can take [limit] and [offset] query parameters
	 * provide [tag], [author] or [favorited] query parameter to filter results
	 */
	@GetMapping
	ResponseEntity<MultipleArticleResponse> getArticleList(
		@RequestParam(defaultValue = "0") int offset,
		@RequestParam(defaultValue = "20") int limit,
		@RequestParam(required = false) String tag,
		@RequestParam(required = false) String author,
		@RequestParam(required = false) String favorited,
		@OptionalUser LoginToken loginToken) {

		GetListArticleCommand command = new GetListArticleCommand(
			new PagingParam(offset, limit), tag, author, favorited);

		Optional<Long> userId = Optional.ofNullable(loginToken)
			.map(LoginToken::getId);

		List<ArticleResult> articleResultList = getArticleListQuery.getArticleList(command, userId);

		return ResponseEntity.ok(MultipleArticleResponse.of(articleResultList));
	}

	/**
	 * Article Feed 리스트 조회
	 * Can take [limit] and [offset] query parameters
	 * return multiple articles created by followed users, ordered by most recent first.
	 */
	@GetMapping("/feed")
	ResponseEntity<MultipleArticleResponse> getFeedArticleList(
		@RequestParam(defaultValue = "0") int offset,
		@RequestParam(defaultValue = "20") int limit,
		@LoginUser LoginToken loginToken) {

		List<ArticleResult> feedArticleResultList =
			getFeedArticleListQuery.getFeedArticleList(new PagingParam(offset,limit), loginToken.getId());

		return ResponseEntity.ok(MultipleArticleResponse.of(feedArticleResultList));
	}
}

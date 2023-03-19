package com.hani.realworld.article.domain;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hani.realworld.common.exception.NotMyArticleException;
import com.hani.realworld.user.domain.User;

class ArticleTest {

	@DisplayName("게시물 팩토리 도메인 테스트 - 성공, Article.without()")
	@Test
	void article_without_test() {
		assertDoesNotThrow(() -> Article.withoutId(
			ARTICLE1.getAuthor(),
			ARTICLE1.getTags(),
			ARTICLE1.getTitle(),
			ARTICLE1.getDescription(),
			ARTICLE1.getBody()));
	}

	@DisplayName("게시물 팩토리 메서드 도메인 테스트 - 성공, Article.with()")
	@Test
	void article_with_test() {
		assertDoesNotThrow(() -> Article.withId(
			ARTICLE1.getId(),
			ARTICLE1.getAuthor(),
			ARTICLE1.getFavorites(),
			ARTICLE1.getTags(),
			ARTICLE1.getSlug(),
			ARTICLE1.getTitle(),
			ARTICLE1.getDescription(),
			ARTICLE1.getBody(),
			ARTICLE1.getCreatedAt(),
			ARTICLE1.getUpdatedAt()));
	}

	@DisplayName("게시물 수정 도메인 테스트 - 성공, article.update()")
	@Test
	void update_Succeeds() {
		final String updatedSlug = "update-title";
		final String updatedTitle = "update title";
		final String updatedDescription = "update description";
		final String updatedBody = "update body";
		// given
		Article article = defaultArticle()
			.withTitle(updatedTitle)
			.withDescription(updatedDescription)
			.withBody(updatedBody)
			.build();

		// when
		Article updatedArticle = article.update(updatedTitle, updatedDescription, updatedBody);

		// then
		assertThat(updatedArticle.getSlug().getSlug()).isEqualTo(updatedSlug);
		assertThat(updatedArticle.getTitle()).isEqualTo(updatedTitle);
		assertThat(updatedArticle.getDescription()).isEqualTo(updatedDescription);
		assertThat(updatedArticle.getBody()).isEqualTo(updatedBody);
	}

	@DisplayName("본인 게시물 확인 도메인 테스트 - 성공, article.checkIsMyArticle()")
	@Test
	void checkIsMyArticle_notThrow_Succeeds() {
		// given
		Article article = ARTICLE1;
		User user = USER1;

		// when
		assertDoesNotThrow(() -> article.checkisMyArticle(user.getId()));
	}

	@DisplayName("본인 게시물 확인 도메인 테스트 - 실패, article.checkIsMyArticle(), throw NotMyArticleException")
	@Test
	void checkIsMyArticle_throw_Succeeds() {
		// given
		Article article = defaultArticle().build();
		User user = USER2;

		// when
		assertThrows(NotMyArticleException.class, () -> article.checkisMyArticle(user.getId()));
	}

	@DisplayName("게시물 즐겨찾기 도메인 테스트 - 성공, article.favorite()")
	@Test
	void favorite_Succeeds() {
		// given
		Article article = defaultArticle().build();

		// when
		article.favorite(USER2.getId());

		// then
		assertThat(article.getFavorites().getFavorites())
			.hasSize(1)
			.contains(USER2.getId());
	}

	@DisplayName("게시물 즐겨찾기 도메인 테스트 - 실패, 이미 팔로우, article.favorite(), throw NotMyArticleException")
	@Test
	void favorite_Failure_alreadyFollowed() {
		// given
		Article article = defaultArticle()
			.withFavorites(new Favorites(USER2.getId()))
			.build();

		// when
		assertThrows(IllegalStateException.class, () -> article.favorite(USER2.getId()));
	}

	@DisplayName("게시물 즐겨찾기 취소 도메인 테스트 - 성공, article.unfavorite()")
	@Test
	void unFavorite_Succeeds() {
		// given
		Article article = defaultArticle()
			.withFavorites(new Favorites(USER2.getId()))
			.build();

		// when
		article.unfavorite(USER2.getId());

		// then
		assertThat(article.getFavorites().getFavorites()).hasSize(0);
	}

	@DisplayName("게시물 즐겨찾기 취소 도메인 테스트 - 실패, 이미 팔로우, article.unfavorite(), throw IllegalStateException")
	@Test
	void unFavorite_Failure() {
		// given
		Article article = defaultArticle().build();

		// when
		assertThrows(IllegalStateException.class, () -> article.unfavorite(USER2.getId()));
	}

	@DisplayName("게시물 즐겨찾기 여부 확인 도메인 테스트 - 성공, article.isFavorited()")
	@Test
	void isFavorited_Succeeds() {
		// given
		Article article = defaultArticle()
			.withFavorites(new Favorites(USER2.getId()))
			.build();

		// when
		boolean result = article.isFavorite(USER2.getId());

		// then
		assertThat(result).isTrue();
	}

}

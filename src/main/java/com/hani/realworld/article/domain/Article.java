package com.hani.realworld.article.domain;

import static com.hani.realworld.user.domain.User.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import com.hani.realworld.common.exception.NotMyArticleException;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class Article {

	/* Article Identification number */
	private final ArticleId id;

	/* Profile who write the Article */
	private final Profile author;

	/* Users who favorited the article. */
	private final Favorites favorites;

	/* Tag Set contains of Article */
	private final Tags tags;

	/* Identification of Article's title */
	private final Slug slug;

	/* Title of Article */
	private final String title;

	/* Description of Article */
	private final String description;

	/* Body of Article */
	private final String body;

	private final LocalDateTime createdAt;

	private final LocalDateTime updatedAt;

	/**
	 * Creates an {@link Article} entity without an ID. Use to create a new entity that is not yet
	 * persisted.
	 */
	public static Article withoutId(
		Profile author,
		Tags tags,
		String title,
		String description,
		String body) {

		Favorites favorites = new Favorites();
		Slug slug = new Slug(title);
		slug.toSlug();

		return new Article(null, author, favorites, tags, slug, title,
			description, body, LocalDateTime.now(), LocalDateTime.now());
	}

	/**
	 * Creates an {@link Article} entity with an ID. Use to reconstitute a persisted entity.
	 */
	public static Article withId(
		ArticleId articleId,
		Profile author,
		Favorites favorites,
		Tags tag,
		Slug slug,
		String title,
		String description,
		String body,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {

		return new Article(articleId, author, favorites, tag, slug, title, description, body, createdAt, updatedAt);
	}

	/**
	 * Creates an {@link Article} entity with an ID. Use to update the persisted entity.
	 * can update ["title", "description", "body"] - Optional
	 */
	public Article update(
		String title,
		String description,
		String body) {

		Slug uSlug = Optional.ofNullable(title)
			.map(t -> {
				Slug slug = new Slug(t);
				slug.toSlug();
				return slug;
			}).orElseGet(() -> this.slug);
		String uTitle = Optional.ofNullable(title).orElseGet(() -> this.title);
		String uDescription = Optional.ofNullable(description).orElseGet(() -> this.description);
		String uBody = Optional.ofNullable(body).orElseGet(() -> this.body);

		return withId(this.id, this.author, this.favorites, this.tags, uSlug, uTitle, uDescription, uBody,
			this.createdAt, LocalDateTime.now());
	}

	/**
	 * check whether article author is login user
	 */
	public void checkisMyArticle(UserId userId) {
		if (!Objects.equals(author.getUser().getId(), userId))
			throw new NotMyArticleException();
	}

	/**
	 * 아티클 즐겨찾기
	 */
	public void favorite(final UserId userId) {
		this.favorites.favorite(userId);
	}

	/**
	 * 아티클 즐겨찾기 취소
	 */
	public void unfavorite(final UserId userId) {
		this.favorites.unfavorite(userId);
	}

	/**
	 * 아티클 즐겨찾기 조회
	 */
	public boolean isFavorite(final UserId userId) {
		return this.favorites.isFavorited(userId);
	}

	@Value
	public static class ArticleId {
		private Long value;
	}
}

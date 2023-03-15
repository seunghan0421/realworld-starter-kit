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

	/* Tag Set contains of Article */
	private final Tags tags;

	/* 아티클을 즐겨찾기 한 사람들 */
	// private List<Favorite> favorites = new ArrayList<>();

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

		Slug slug = new Slug(title);
		slug.toSlug();

		return new Article(null, author, tags, slug, title,
			description, body, LocalDateTime.now(), LocalDateTime.now());
	}

	/**
	 * Creates an {@link Profile} entity with an ID. Use to reconstitute a persisted entity.
	 */
	public static Article withId(
		ArticleId articleId,
		Profile author,
		Tags tag,
		Slug slug,
		String title,
		String description,
		String body,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {

		return new Article(articleId, author, tag, slug, title, description, body, createdAt, updatedAt);
	}

	/**
	 * Creates an {@link User} entity with an ID. Use to update the persisted entity.
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

		return withId(this.id, this.author, this.tags, uSlug, uTitle, uDescription, uBody,
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
	// public void favorite(final User user) {
	// 	this.favorites.add(new Favorite(user, this));
	// }

	/**
	 * 아티클 즐겨찾기 취소
	 */
	// public void unfavorite(final User user) {
	// 	favorites.stream()
	// 		.filter(favorite -> favorite.isSame(user, this))
	// 		.findAny().ifPresent(favorite -> favorites.remove(favorite));
	// }

	/**
	 * 아티클 즐겨찾기 조회
	 */
	// public boolean isFavorite(final User user) {
	// 	return favorites.stream().map(Favorite::getUser)
	// 		.anyMatch(wrapper -> wrapper.equals(user));
	// }

	/**
	 * 이 아티클을 즐겨찾기 한 사람 수
	 */
	// public int countOfFavorites() {
	// 	return this.favorites.size();
	// }

	@Value
	public static class ArticleId {
		private Long value;
	}
}

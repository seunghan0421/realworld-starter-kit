package com.hani.realworld.article.domain;

import static com.hani.realworld.user.domain.User.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.hani.realworld.common.exception.article.AlreadyFavortiedArticleException;
import com.hani.realworld.common.exception.article.NotFavoritedArticleException;

import lombok.NonNull;

public class Favorites {

	/**
	 * Those who favorites this article.
	 */
	private Set<UserId> users;

	public Favorites() {
		this.users = new HashSet<>();
	}

	public Favorites(@NonNull Set<UserId> users) {
		this.users = new HashSet<>(users);
	}

	public Favorites(@NonNull UserId... users) {
		this.users = new HashSet<>(Arrays.asList(users));
	}

	public Set<UserId> getFavorites() {
		return Collections.unmodifiableSet(this.users);
	}

	/**
	 * The method to favorite the article.
	 * Put the user id in the user list.
	 *
	 * @throws IllegalStateException if user already favorite the article
	 */
	public void favorite(UserId userId) {
		if(isFavorited(userId))
			throw new AlreadyFavortiedArticleException();

		this.users.add(userId);
	}

	/**
	 * The method to unfavorite the article.
	 * Remove the user id in the user list.
	 *
	 * @throws IllegalStateException if user not favorited the article yet
	 */
	public void unfavorite(UserId userId) {
		if(!isFavorited(userId))
			throw new NotFavoritedArticleException();

		this.users.remove(userId);
	}

	/**
	 * favorite check method
	 *
	 * @return boolean value whether user favorite the article
	 */
	public boolean isFavorited(UserId userId) {
		return this.users.parallelStream()
			.anyMatch(id -> id.equals(userId));
	}
}

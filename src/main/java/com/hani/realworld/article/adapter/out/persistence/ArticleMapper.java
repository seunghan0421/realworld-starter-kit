package com.hani.realworld.article.adapter.out.persistence;

import static com.hani.realworld.article.domain.Article.withId;
import static com.hani.realworld.article.domain.Article.*;
import static com.hani.realworld.user.domain.User.*;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hani.realworld.article.domain.Article;
import com.hani.realworld.article.domain.Favorites;
import com.hani.realworld.article.domain.Slug;
import com.hani.realworld.article.domain.Tags;
import com.hani.realworld.user.domain.Profile;

@Component
public class ArticleMapper {

	ArticleJpaEntity mapToArticleJpaEntity(Article article) {
		return new ArticleJpaEntity(
			article.getId() == null ? null : article.getId().getValue(),
			article.getAuthor().getId().getValue(),
			article.getTags().getTags(),
			article.getFavorites().getFavorites().stream()
					.map(UserId::getValue)
						.collect(Collectors.toSet()),
			article.getSlug().getSlug(),
			article.getTitle(),
			article.getDescription(),
			article.getBody(),
			article.getCreatedAt(),
			article.getUpdatedAt());
	}

	Article mapToArticleEntity(ArticleJpaEntity articleJpaEntity, Profile author) {
		return withId(
			new ArticleId(articleJpaEntity.getId()),
			author,
			new Favorites(articleJpaEntity.getUsers().stream()
				.map(UserId::new)
				.collect(Collectors.toSet())),
			new Tags(articleJpaEntity.getTags()),
			new Slug(articleJpaEntity.getSlug()),
			articleJpaEntity.getTitle(),
			articleJpaEntity.getDescription(),
			articleJpaEntity.getBody(),
			articleJpaEntity.getCreatedAt(),
			articleJpaEntity.getUpdatedAt());
	}

}

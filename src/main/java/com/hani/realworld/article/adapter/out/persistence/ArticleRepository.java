package com.hani.realworld.article.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<ArticleJpaEntity, Long> {

	@Query("select a from ArticleJpaEntity a "
		+ "where a.slug = :slug")
	Optional<ArticleJpaEntity> getArticleWithSlug(String slug);
}

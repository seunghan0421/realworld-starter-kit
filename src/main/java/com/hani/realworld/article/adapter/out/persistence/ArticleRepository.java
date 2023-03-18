package com.hani.realworld.article.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hani.realworld.article.domain.Article;

public interface ArticleRepository extends JpaRepository<ArticleJpaEntity, Long> {

	@Query("select a from ArticleJpaEntity a "
		+ "where a.slug = :slug")
	Optional<ArticleJpaEntity> getArticleWithSlug(String slug);

	@Query(value = "select distinct t.tag_value from tag t ", nativeQuery = true)
	Set<String> getAllTags();

}

package com.hani.realworld.article.adapter.out.persistence;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hani.realworld.article.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "article")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ArticleJpaEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "article_id")
	private Long id;

	@Column(name = "author_id")
	private Long authorId;

	@ElementCollection(targetClass = String.class)
	private Set<String> tags;

	private String slug;

	private String title;

	private String description;

	private String body;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	void update(Article article) {
		this.tags = article.getTags().getTags();
		this.slug = article.getSlug().getSlug();
		this.title = article.getTitle();
		this.description = article.getDescription();
		this.body = article.getBody();
		this.updatedAt = article.getUpdatedAt();
	}

}

package com.hani.realworld.article.adapter.out.persistence;

import static com.hani.realworld.user.domain.User.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@CollectionTable(
		name = "tag",
		joinColumns = @JoinColumn(name = "article_id")
	)
	@Column(name = "tag_value")
	private Set<String> tags;

	@ElementCollection(targetClass = Long.class)
	private Set<Long> users;

	private String slug;

	private String title;

	private String description;

	private String body;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	void update(Article article) {
		this.slug = article.getSlug().getValue();
		this.title = article.getTitle();
		this.description = article.getDescription();
		this.body = article.getBody();
		this.updatedAt = article.getUpdatedAt();

		this.users = article.getFavorites().getFavorites().stream()
			.map(UserId::getValue)
			.collect(Collectors.toSet());
	}

}

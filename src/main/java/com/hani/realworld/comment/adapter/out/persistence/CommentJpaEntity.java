package com.hani.realworld.comment.adapter.out.persistence;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CommentJpaEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	private Long articleId;

	private Long authorId;

	private String body;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}

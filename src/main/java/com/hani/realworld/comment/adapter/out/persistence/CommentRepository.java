package com.hani.realworld.comment.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<CommentJpaEntity, Long> {

	@Query("select c from CommentJpaEntity c "
		+ "where c.articleId = :articleId")
	List<CommentJpaEntity> getCommentsWithArticleId(Long articleId);
}

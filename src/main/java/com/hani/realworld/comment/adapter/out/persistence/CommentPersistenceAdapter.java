package com.hani.realworld.comment.adapter.out.persistence;

import static com.hani.realworld.article.domain.Article.*;
import static com.hani.realworld.comment.domain.Comment.*;
import static com.hani.realworld.user.domain.Profile.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.hani.realworld.comment.application.port.in.result.CommentResult;
import com.hani.realworld.comment.application.port.out.CreateCommentStatePort;
import com.hani.realworld.comment.application.port.out.DeleteCommentWithCommentIdPort;
import com.hani.realworld.comment.application.port.out.GetCommentWithIdPort;
import com.hani.realworld.comment.application.port.out.GetMultipleCommentWithArticleIdPort;
import com.hani.realworld.comment.domain.Comment;
import com.hani.realworld.common.annotation.PersistenceAdapter;
import com.hani.realworld.user.application.port.out.LoadProfileWithProfileIdPort;
import com.hani.realworld.user.domain.Profile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class CommentPersistenceAdapter implements
	CreateCommentStatePort,
	GetCommentWithIdPort,
	GetMultipleCommentWithArticleIdPort,
	DeleteCommentWithCommentIdPort {

	private final CommentRepository commentRepository;
	private final CommentMapper commentMapper;

	private final LoadProfileWithProfileIdPort loadProfileWithProfileIdPort;

	@Override
	public Comment createComment(Comment comment) {
		CommentJpaEntity commentJpaEntity = commentMapper.mapToCommentJpaEntity(comment);

		CommentJpaEntity savedEntity = commentRepository.save(commentJpaEntity);

		return commentMapper.mapToCommentEntity(savedEntity, comment.getAuthor());
	}

	@Override
	public void deleteComment(CommentId commentId) {
		commentRepository.deleteById(commentId.getValue());
	}

	@Override
	public Comment getComment(CommentId commentId) {
		CommentJpaEntity commentJpaEntity = commentRepository.findById(commentId.getValue())
			.orElseThrow(EntityNotFoundException::new);
		Profile author = loadProfileWithProfileIdPort
			.loadProfileWithProfileId(new ProfileId(commentJpaEntity.getAuthorId()));

		return commentMapper.mapToCommentEntity(commentJpaEntity, author);
	}

	@Override
	public List<Comment> getCommentsWithArticleId(ArticleId articleId) {
		List<CommentJpaEntity> commentJpaEntities =
			commentRepository.getCommentsWithArticleId(articleId.getValue());

		return commentJpaEntities.stream()
			.map(entity -> {
				Profile author = loadProfileWithProfileIdPort
					.loadProfileWithProfileId(new ProfileId(entity.getAuthorId()));

				return commentMapper.mapToCommentEntity(entity, author);
			})
			.collect(Collectors.toList());
	}
}

package com.hani.realworld.comment.application.service;

import static com.hani.realworld.user.domain.User.*;

import java.util.Optional;

import javax.transaction.Transactional;

import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.comment.application.port.in.AddCommentUseCase;
import com.hani.realworld.comment.application.port.in.command.AddCommentCommand;
import com.hani.realworld.comment.application.port.in.result.CommentResult;
import com.hani.realworld.comment.application.port.out.CreateCommentStatePort;
import com.hani.realworld.comment.domain.Comment;
import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@UseCase
public class AddCommentService implements AddCommentUseCase {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort;
	private final LoadProfileWithUserIdPort loadProfileWithUserIdPort;
	private final CreateCommentStatePort createCommentStatePort;

	private final GetProfileQuery getProfileQuery;

	@Override
	public CommentResult addComment(AddCommentCommand command, String slug, Long id) {
		Article article = loadArticleWithSlugPort.load(slug);
		Profile author = loadProfileWithUserIdPort.loadProfileWithUserId(new UserId(id));

		Comment comment = Comment.withoutId(article.getId().getValue(), author, command.getBody());
		Comment savedComment = createCommentStatePort.createComment(comment);

		ProfileResult profileResult = getProfileQuery.getProfile(author.getUser().getUsername(), Optional.of(id));
		return CommentResult.of(savedComment, profileResult);
	}
}

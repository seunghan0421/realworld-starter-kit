package com.hani.realworld.comment.adapter.in;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.comment.adapter.in.dto.AddCommentRequest;
import com.hani.realworld.comment.adapter.in.dto.CommentResponse;
import com.hani.realworld.comment.adapter.in.dto.MultipleCommentResponse;
import com.hani.realworld.comment.application.port.in.AddCommentUseCase;
import com.hani.realworld.comment.application.port.in.DeleteCommentUseCase;
import com.hani.realworld.comment.application.port.in.GetCommentsQuery;
import com.hani.realworld.comment.application.port.in.command.AddCommentCommand;
import com.hani.realworld.comment.application.port.in.result.CommentResult;
import com.hani.realworld.infra.jwt.LoginToken;
import com.hani.realworld.infra.jwt.LoginUser;
import com.hani.realworld.infra.jwt.OptionalUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class CommentController {

	private final AddCommentUseCase addCommentUseCase;
	private final DeleteCommentUseCase deleteCommentUseCase;

	private final GetCommentsQuery getCommentsQuery;

	@PostMapping("/{slug}/comments")
	ResponseEntity<CommentResponse> addCommentToArticle(
		@RequestBody AddCommentRequest request,
		@PathVariable("slug") String slug,
		@LoginUser LoginToken loginToken) {

		AddCommentCommand command = new AddCommentCommand(request.getBody());

		CommentResult commentResult = addCommentUseCase.addComment(command, slug, loginToken.getId());

		return ResponseEntity
			.created(URI.create(String.format("/api/articles/%s/comments/%d", slug, commentResult.getId())))
			.body(CommentResponse.of(commentResult));
	}

	@GetMapping("/{slug}/comments")
	ResponseEntity<MultipleCommentResponse> getAllCommentsOfArticle(
		@PathVariable("slug") String slug,
		@OptionalUser LoginToken loginToken) {

		Optional<Long> userId = Optional.ofNullable(loginToken)
			.map(LoginToken::getId);

		List<CommentResult> commentResults = getCommentsQuery.getComments(slug, userId);

		return ResponseEntity.ok(MultipleCommentResponse.of(commentResults));
	}

	@DeleteMapping("/{slug}/comments/{id}")
	ResponseEntity<Void> deleteCommentOfArticle(
		@PathVariable("id") Long commentId,
		@LoginUser LoginToken loginToken) {

		deleteCommentUseCase.deleteComment(commentId, loginToken.getId());

		return ResponseEntity.noContent().build();
	}
}

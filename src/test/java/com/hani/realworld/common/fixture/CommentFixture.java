package com.hani.realworld.common.fixture;

import static com.hani.realworld.comment.domain.Comment.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.mockito.Mockito;

import com.hani.realworld.comment.adapter.in.dto.AddCommentRequest;
import com.hani.realworld.comment.domain.Comment;
import com.hani.realworld.user.domain.Profile;

public class CommentFixture {

	public static CommentBuilder defaultComment() {
		return new CommentBuilder()
			.withId(new CommentId(1L))
			.withAuthor(PROFILE1)
			.withBody("user1 comment1");
	}

	public static class CommentBuilder {
		private CommentId id;
		private Profile author;
		private String body;

		public CommentBuilder withId(CommentId id) {
			this.id = id;

			return this;
		}

		public CommentBuilder withAuthor(Profile author) {
			this.author = author;

			return this;
		}

		public CommentBuilder withBody(String body) {
			this.body = body;

			return this;
		}

		public Comment build() {
			return Comment.withId(
				this.id,
				this.author,
				this.body,
				LocalDateTime.now(),
				LocalDateTime.now());
		}
	}

	public static final Comment COMMENT1 = defaultComment()
		.withId(new CommentId(1L))
		.withAuthor(PROFILE1)
		.withBody("user1 comment1")
		.build();

	public static final Comment COMMENT2 = defaultComment()
		.withId(new CommentId(2L))
		.withAuthor(PROFILE2)
		.withBody("user2 comment2")
		.build();

	public static Comment getMockComment1() {
		Comment comment = Mockito.mock(Comment.class);

		given(comment.getId()).willReturn(COMMENT1.getId());
		given(comment.getAuthor()).willReturn(COMMENT1.getAuthor());
		given(comment.getBody()).willReturn(COMMENT1.getBody());

		return comment;
	}

	public static AddCommentRequest ADD_COMMENT_REQUEST =
		new AddCommentRequest(COMMENT1.getBody());

}

package com.hani.realworld.comment.application.service;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.CommentFixture.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.comment.application.port.in.command.AddCommentCommand;
import com.hani.realworld.comment.application.port.in.result.CommentResult;
import com.hani.realworld.comment.application.port.out.CreateCommentStatePort;
import com.hani.realworld.comment.domain.Comment;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;

class AddCommentServiceTest {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort =
		Mockito.mock(LoadArticleWithSlugPort.class);

	private final LoadProfileWithUserIdPort loadProfileWithUserIdPort =
		Mockito.mock(LoadProfileWithUserIdPort.class);

	private final CreateCommentStatePort createCommentStatePort =
		Mockito.mock(CreateCommentStatePort.class);

	private final GetProfileQuery getProfileQuery =
		Mockito.mock(GetProfileQuery.class);

	private final AddCommentService addCommentService =
		new AddCommentService(
			loadArticleWithSlugPort,
			loadProfileWithUserIdPort,
			createCommentStatePort,
			getProfileQuery);

	@DisplayName("댓글 생성 서비스 테스트 - 성공")
	@Test
	void createArticle_Succeeds() {
		// given
		Comment comment = COMMENT1;
		AddCommentCommand command = new AddCommentCommand(COMMENT1.getBody());

		MockedStatic<Comment> mockCommentClass = mockStatic(Comment.class);
		mockCommentClass.when(() -> Comment.withoutId(any(), any(), any()))
			.thenReturn(comment);

		given(loadArticleWithSlugPort.load(eq(ARTICLE1.getSlug().getSlug())))
			.willReturn(ARTICLE1);
		given(loadProfileWithUserIdPort.loadProfileWithUserId(eq(USER1.getId())))
			.willReturn(PROFILE1);
		given(createCommentStatePort.createComment(comment))
			.willReturn(COMMENT1);
		given(getProfileQuery.getProfile(eq(USER1.getUsername()), eq(Optional.of(USER1.getId().getValue()))))
			.willReturn(ProfileResult.of(PROFILE1, false));

		// when
		CommentResult result = addCommentService.addComment(command, ARTICLE1.getSlug().getSlug(), USER1.getId().getValue());

		// then
		assertThat(result.getId()).isEqualTo(COMMENT1.getId().getValue());
		assertThat(result.getAuthor().getUsername()).isEqualTo(USER1.getUsername());
		assertThat(result.getBody()).isEqualTo(COMMENT1.getBody());
		assertThat(result.getCreatedAt()).isNotNull();
		assertThat(result.getUpdatedAt()).isNotNull();

		then(loadArticleWithSlugPort).should().load(eq(ARTICLE1.getSlug().getSlug()));
		then(loadProfileWithUserIdPort).should().loadProfileWithUserId(eq(USER1.getId()));
		then(createCommentStatePort).should().createComment(eq(comment));
		then(getProfileQuery).should().getProfile(eq(USER1.getUsername()), eq(Optional.of(USER1.getId().getValue())));

		mockCommentClass.close();
	}

}

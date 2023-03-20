package com.hani.realworld.comment.application.service;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.CommentFixture.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.comment.application.port.in.result.CommentResult;
import com.hani.realworld.comment.application.port.out.LoadMultipleCommentWithArticleIdPort;
import com.hani.realworld.comment.domain.Comment;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

class GetCommentsServiceTest {

	private final LoadArticleWithSlugPort loadArticleWithSlugPort =
		Mockito.mock(LoadArticleWithSlugPort.class);

	private final LoadMultipleCommentWithArticleIdPort loadMultipleCommentWithArticleIdPort =
		Mockito.mock(LoadMultipleCommentWithArticleIdPort.class);

	private final GetProfileQuery getProfileQuery =
		Mockito.mock(GetProfileQuery.class);

	private final GetCommentsService getCommentsService =
		new GetCommentsService(loadArticleWithSlugPort, loadMultipleCommentWithArticleIdPort, getProfileQuery);

	@DisplayName("댓글 리스트 조회 서비스 테스트 - 성공")
	@Test
	void getComments_Succeeds() {
		// given
		List<Comment> comments = List.of(COMMENT1, COMMENT2);
		ProfileResult profileResult = ProfileResult.of(PROFILE1, false);

		given(loadArticleWithSlugPort.load(eq(ARTICLE1.getSlug().getValue())))
			.willReturn(ARTICLE1);
		given(loadMultipleCommentWithArticleIdPort.getCommentsWithArticleId(eq(ARTICLE1.getId())))
			.willReturn(comments);
		given(getProfileQuery.getProfile(any(), any())).willReturn(profileResult);

		// when
		List<CommentResult> result = getCommentsService.getComments(
			ARTICLE1.getSlug().getValue(),
			Optional.of(USER1.getId().getValue()));

		// then
		assertThat(result).size().isEqualTo(2);
		assertThat(result.stream().map(CommentResult::getId))
			.contains(COMMENT1.getId().getValue(), COMMENT2.getId().getValue());
		assertThat(result.stream().map(CommentResult::getAuthor).map(ProfileResult::getUsername))
			.containsOnly(PROFILE1.getUser().getUsername());

		then(loadArticleWithSlugPort).should().load(eq(ARTICLE1.getSlug().getValue()));
		then(loadMultipleCommentWithArticleIdPort).should().getCommentsWithArticleId(eq(ARTICLE1.getId()));
		then(getProfileQuery).should()
			.getProfile(eq(PROFILE1.getUser().getUsername()), eq(Optional.of(USER1.getId().getValue())));
	}
}

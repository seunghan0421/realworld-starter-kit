package com.hani.realworld.article.application.service;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
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

import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.LoadFeedArticleListPort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

class GetFeedArticleListServiceTest {

	private final LoadFeedArticleListPort loadFeedArticleListPort =
		Mockito.mock(LoadFeedArticleListPort.class);

	private final GetProfileQuery getProfileQuery =
		Mockito.mock(GetProfileQuery.class);

	private final GetFeedArticleListService getFeedArticleListService =
		new GetFeedArticleListService(loadFeedArticleListPort, getProfileQuery);

	@DisplayName("피드 게시물 리스트 조회 서비스 테스트 - 성공")
	@Test
	void getFeedArticleList_Succeeds() {
		// given
		List<Article> articles = List.of(ARTICLE1, ARTICLE2);
		ProfileResult profileResult = ProfileResult.of(PROFILE1, false);

		given(loadFeedArticleListPort.loadFeedArticleList(eq(PAGING_PARAM), eq(USER1.getId())))
			.willReturn(articles);
		given(getProfileQuery.getProfile(any(), any())).willReturn(profileResult);

		// when
		List<ArticleResult> result =
			getFeedArticleListService.getFeedArticleList(PAGING_PARAM, USER1.getId().getValue());

		// then
		assertThat(result).size().isEqualTo(2);
		assertThat(result.stream().map(ArticleResult::getSlug))
			.contains(ARTICLE1.getSlug().getValue(), ARTICLE2.getSlug().getValue());
		assertThat(result.stream().map(ArticleResult::getProfileResult).map(ProfileResult::getUsername))
			.containsOnly(PROFILE1.getUser().getUsername());

		then(loadFeedArticleListPort).should().loadFeedArticleList(eq(PAGING_PARAM), eq(USER1.getId()));
		then(getProfileQuery).should()
			.getProfile(eq(PROFILE1.getUser().getUsername()), eq(Optional.of(USER1.getId().getValue())));
	}

}

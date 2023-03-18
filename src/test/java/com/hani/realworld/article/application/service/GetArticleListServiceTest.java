package com.hani.realworld.article.application.service;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.in.command.GetListArticleCommand;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.application.port.out.LoadArticleListWithParamsPort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

class GetArticleListServiceTest {

	private final LoadArticleListWithParamsPort loadArticleListWithParamsPort =
		Mockito.mock(LoadArticleListWithParamsPort.class);

	private final GetProfileQuery getProfileQuery =
		Mockito.mock(GetProfileQuery.class);

	private final GetArticleListService getArticleListService =
		new GetArticleListService(loadArticleListWithParamsPort, getProfileQuery);

	@Test
	void getFeedArticleList_Succeeds() {
		// given
		List<Article> articles = List.of(ARTICLE1, ARTICLE2);
		ProfileResult profileResult = ProfileResult.of(PROFILE1, false);

		final GetListArticleCommand command =
			new GetListArticleCommand(PAGING_PARAM, "tag1", "author1", "favorited1");

		given(loadArticleListWithParamsPort.loadArticleList(eq(PAGING_PARAM),
			eq(command.getTag()), eq(command.getAuthor()), eq(command.getFavorited())))
			.willReturn(articles);
		given(getProfileQuery.getProfile(any(), any())).willReturn(profileResult);

		// when
		List<ArticleResult> result =
			getArticleListService.getArticleList(command, Optional.of(USER1.getId().getValue()));

		// then
		assertThat(result).size().isEqualTo(2);
		assertThat(result.stream().map(ArticleResult::getSlug))
			.contains(ARTICLE1.getSlug().getSlug(), ARTICLE2.getSlug().getSlug());
		assertThat(result.stream().map(ArticleResult::getProfileResult).map(ProfileResult::getUsername))
			.containsOnly(PROFILE1.getUser().getUsername());

		then(loadArticleListWithParamsPort).should().loadArticleList(eq(PAGING_PARAM)
			, eq(command.getTag()), eq(command.getAuthor()), eq(command.getFavorited()));
		then(getProfileQuery).should()
			.getProfile(eq(PROFILE1.getUser().getUsername()), eq(Optional.of(USER1.getId().getValue())));
	}

}

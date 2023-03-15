package com.hani.realworld.common.fixture;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.mockito.Mockito;

import com.hani.realworld.article.adapter.in.dto.ArticleResponse;
import com.hani.realworld.article.adapter.in.dto.CreateArticleRequest;
import com.hani.realworld.article.adapter.in.dto.UpdateArticleRequest;
import com.hani.realworld.article.application.port.in.result.ArticleResult;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.article.domain.Slug;
import com.hani.realworld.article.domain.Tags;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.domain.Profile;

public class ArticleFixture {

	public static ArticleBuilder defaultArticle() {
		return new ArticleBuilder().
			withArticleId(new Article.ArticleId(1L))
			.withAuthor(PROFILE1)
			.withTags("Spring", "React")
			.withTitle("Spring with React")
			.withDescription("this is spring & react project")
			.withBody("body about Project");
	}

	public static class ArticleBuilder {
		private Article.ArticleId articleId;
		private Profile author;
		private Tags tags;
		private Slug slug;
		private String title;
		private String description;
		private String body;

		public ArticleBuilder withArticleId(Article.ArticleId articleId) {
			this.articleId = articleId;
			return this;
		}

		public ArticleBuilder withAuthor(Profile author) {
			this.author = author;

			return this;
		}

		public ArticleBuilder withTags(String... tags) {
			this.tags = tags.length > 0 ? new Tags(tags) : new Tags();

			return this;
		}

		public ArticleBuilder withTitle(String title) {
			this.slug = new Slug(title);
			this.slug.toSlug();
			this.title = title;

			return this;
		}

		public ArticleBuilder withDescription(String description) {
			this.description = description;

			return this;
		}

		public ArticleBuilder withBody(String body) {
			this.body = body;

			return this;
		}

		public Article build() {
			return Article.withId(
				this.articleId,
				this.author,
				this.tags,
				this.slug,
				this.title,
				this.description,
				this.body,
				LocalDateTime.now(),
				LocalDateTime.now());
		}
	}

	public static final Article ARTICLE1 = defaultArticle()
		.withArticleId(new Article.ArticleId(1L))
		.withAuthor(PROFILE1)
		.withTags("user1")
		.withTitle("user1 article")
		.withDescription("user1 description")
		.withBody("user1 body")
		.build();

	public static final Article ARTICLE1_WITHOUT_TAG = defaultArticle()
		.withArticleId(new Article.ArticleId(1L))
		.withAuthor(PROFILE1)
		.withTags()
		.withTitle("user1 article")
		.withDescription("user1 description")
		.withBody("user1 body")
		.build();

	public static final Article ARTICLE2 = defaultArticle()
		.withArticleId(new Article.ArticleId(2L))
		.withAuthor(PROFILE2)
		.withTags("user2")
		.withTitle("user2 article")
		.withDescription("user2 description")
		.withBody("user2 body")
		.build();

	public static Article getMockARTICLE1() {
		Article article = Mockito.mock(Article.class);

		given(article.getId()).willReturn(ARTICLE1.getId());
		given(article.getAuthor()).willReturn(ARTICLE1.getAuthor());
		given(article.getSlug()).willReturn(ARTICLE1.getSlug());
		given(article.getTitle()).willReturn(ARTICLE1.getTitle());
		given(article.getDescription()).willReturn(ARTICLE1.getDescription());
		given(article.getBody()).willReturn(ARTICLE1.getBody());
		given(article.getTags()).willReturn(ARTICLE1.getTags());
		given(article.getCreatedAt()).willReturn(ARTICLE1.getCreatedAt());
		given(article.getUpdatedAt()).willReturn(ARTICLE1.getUpdatedAt());

		return article;
	}

	public static Article getMockARTICLE2() {
		Article article = Mockito.mock(Article.class);

		given(article.getId()).willReturn(ARTICLE2.getId());
		given(article.getAuthor()).willReturn(ARTICLE2.getAuthor());
		given(article.getSlug()).willReturn(ARTICLE2.getSlug());
		given(article.getTitle()).willReturn(ARTICLE2.getTitle());
		given(article.getDescription()).willReturn(ARTICLE2.getDescription());
		given(article.getBody()).willReturn(ARTICLE2.getBody());
		given(article.getTags()).willReturn(ARTICLE2.getTags());
		given(article.getCreatedAt()).willReturn(ARTICLE2.getCreatedAt());
		given(article.getUpdatedAt()).willReturn(ARTICLE2.getUpdatedAt());

		return article;
	}

	public static final CreateArticleRequest CREATE_ARTICLE_REQUEST =
		new CreateArticleRequest(
			ARTICLE1.getTitle(),
			ARTICLE1.getDescription(),
			ARTICLE1.getBody(),
			new ArrayList<>(ARTICLE1.getTags().getTags()));

	public static final UpdateArticleRequest UPDATE_ARTICLE_REQUEST =
		new UpdateArticleRequest(
			ARTICLE2.getTitle(),
			ARTICLE2.getDescription(),
			ARTICLE2.getBody());

	public static ArticleResponse getArticleResponse(Article article) {
		return ArticleResponse.of(ArticleResult.of(
			article,
			ProfileResult.of(article.getAuthor(), false)));
	}
}

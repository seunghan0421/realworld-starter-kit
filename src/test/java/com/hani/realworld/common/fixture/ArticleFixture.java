package com.hani.realworld.common.fixture;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.mockito.Mockito;

import com.hani.realworld.article.adapter.in.dto.request.CreateArticleRequest;
import com.hani.realworld.article.adapter.in.dto.request.UpdateArticleRequest;
import com.hani.realworld.article.application.port.in.command.PagingParam;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.article.domain.Favorites;
import com.hani.realworld.article.domain.Slug;
import com.hani.realworld.article.domain.Tags;
import com.hani.realworld.user.domain.Profile;

public class ArticleFixture {

	public static ArticleBuilder defaultArticle() {
		return new ArticleBuilder().
			withArticleId(new Article.ArticleId(1L))
			.withAuthor(PROFILE1)
			.withTags("Spring", "React")
			.withTitle("Spring with React")
			.withDescription("this is spring & react project")
			.withBody("body about Project")
			.withFavorites(new Favorites());
	}

	public static final Article ARTICLE1 = defaultArticle()
		.withArticleId(new Article.ArticleId(1L))
		.withAuthor(PROFILE1)
		.withTags("user1")
		.withFavorites(new Favorites())
		.withTitle("user1 article")
		.withDescription("user1 description")
		.withBody("user1 body")
		.build();

	public static final Article ARTICLE2 = defaultArticle()
		.withArticleId(new Article.ArticleId(2L))
		.withAuthor(PROFILE2)
		.withTags("user2")
		.withFavorites(new Favorites())
		.withTitle("user2 article")
		.withDescription("user2 description")
		.withBody("user2 body")
		.build();

	public static Article getMockARTICLE1() {
		Article article = Mockito.mock(Article.class);

		given(article.getId()).willReturn(ARTICLE1.getId());
		given(article.getAuthor()).willReturn(ARTICLE1.getAuthor());
		given(article.getFavorites()).willReturn(ARTICLE1.getFavorites());
		given(article.getTags()).willReturn(ARTICLE1.getTags());
		given(article.getSlug()).willReturn(ARTICLE1.getSlug());
		given(article.getTitle()).willReturn(ARTICLE1.getTitle());
		given(article.getDescription()).willReturn(ARTICLE1.getDescription());
		given(article.getBody()).willReturn(ARTICLE1.getBody());
		given(article.getFavorites()).willReturn(ARTICLE1.getFavorites());
		given(article.getCreatedAt()).willReturn(ARTICLE1.getCreatedAt());
		given(article.getUpdatedAt()).willReturn(ARTICLE1.getUpdatedAt());

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

	public static final PagingParam PAGING_PARAM = new PagingParam();

	public static class ArticleBuilder {
		private Article.ArticleId articleId;
		private Profile author;
		private Favorites favorites;
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

		public ArticleBuilder withFavorites(Favorites favorites) {
			this.favorites = favorites;

			return this;
		}

		public Article build() {
			return Article.withId(
				this.articleId,
				this.author,
				this.favorites,
				this.tags,
				this.slug,
				this.title,
				this.description,
				this.body,
				LocalDateTime.now(),
				LocalDateTime.now());
		}
	}
}

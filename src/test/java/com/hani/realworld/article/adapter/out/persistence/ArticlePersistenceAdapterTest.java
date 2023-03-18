package com.hani.realworld.article.adapter.out.persistence;

import static com.hani.realworld.article.domain.Article.*;
import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.hani.realworld.article.domain.Article;
import com.hani.realworld.config.JpaConfig;
import com.hani.realworld.user.adapter.out.persistence.UserMapper;
import com.hani.realworld.user.adapter.out.persistence.UserPersistenceAdapter;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(
	{
		ArticlePersistenceAdapter.class, ArticleMapper.class,
		ArticleQueryRepository.class, JpaConfig.class,
		UserPersistenceAdapter.class, UserMapper.class
	})
class ArticlePersistenceAdapterTest {

	@Autowired
	private ArticlePersistenceAdapter adapter;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleQueryRepository articleQueryRepository;

	@Sql(
		value = {"UserPersistenceAdapterTest.sql", "ProfilePersistenceAdapterTest.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

	@Test
	void createArticle_succeeds() {
		// given
		Article article = defaultArticle()
			.withArticleId(null)
			.withAuthor(PROFILE1)
			.withTitle(ARTICLE1.getTitle())
			.withDescription(ARTICLE1.getDescription())
			.withBody(ARTICLE1.getBody())
			.withTags("user1")
			.build();

		// when
		adapter.create(article);

		// then
		assertThat(articleRepository.count()).isEqualTo(1);

		ArticleJpaEntity savedEntity = articleRepository.findAll().get(0);

		assertThat(savedEntity.getAuthorId()).isEqualTo(PROFILE1.getId().getValue());
		assertThat(savedEntity.getSlug()).isEqualTo(ARTICLE1.getSlug().getSlug());
		assertThat(savedEntity.getTitle()).isEqualTo(ARTICLE1.getTitle());
		assertThat(savedEntity.getDescription()).isEqualTo(ARTICLE1.getDescription());
		assertThat(savedEntity.getBody()).isEqualTo(ARTICLE1.getBody());
		assertThat(savedEntity.getTags()).contains("user1").size().isEqualTo(1);
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql",
			"ArticlePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
	)
	@Test
	void load_Article_with_slug_succeeds() {
		// when
		Article article = adapter.load(ARTICLE1.getSlug().getSlug());

		// then
		assertThat(article.getAuthor().getId()).isEqualTo(PROFILE1.getId());
		assertThat(article.getTags().getTags()).contains("user1").size().isEqualTo(1);
		assertThat(article.getSlug()).isEqualTo(ARTICLE1.getSlug());
		assertThat(article.getTitle()).isEqualTo(ARTICLE1.getTitle());
		assertThat(article.getDescription()).isEqualTo(ARTICLE1.getDescription());
		assertThat(article.getBody()).isEqualTo(ARTICLE1.getBody());
		assertThat(article.getCreatedAt()).isNotNull();
		assertThat(article.getUpdatedAt()).isNotNull();
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql",
			"ArticlePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void update_Article_succeeds() {
		// given
		Article updatedArticle = defaultArticle()
			.withArticleId(new ArticleId(1L))
			.withTitle(ARTICLE2.getTitle())
			.withDescription(ARTICLE2.getDescription())
			.withBody(ARTICLE2.getBody())
			.build();

		// when
		adapter.update(updatedArticle);

		// then
		ArticleJpaEntity savedEntity = articleRepository.findById(1L).get();
		assertThat(savedEntity.getSlug()).isEqualTo(ARTICLE2.getSlug().getSlug());
		assertThat(savedEntity.getTitle()).isEqualTo(ARTICLE2.getTitle());
		assertThat(savedEntity.getDescription()).isEqualTo(ARTICLE2.getDescription());
		assertThat(savedEntity.getBody()).isEqualTo(ARTICLE2.getBody());
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql",
			"ArticlePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void delete_Article_succeeds() {
		// when
		adapter.delete(ARTICLE1.getId());

		// then
		List<ArticleJpaEntity> articleJpaEntityList = articleRepository.findAll();
		assertThat(articleJpaEntityList).size().isEqualTo(1);
		assertThat(articleJpaEntityList.get(0).getId()).isEqualTo(ARTICLE2.getId().getValue());
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql",
			"ArticlePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void getArticleList_withTags_succeeds() {
		// when
		List<Article> articles =
			adapter.loadArticleList(PAGING_PARAM, null, null, null);

		// then
		assertThat(articles.stream().map(Article::getId).map(ArticleId::getValue))
			.contains(ARTICLE1.getId().getValue(),ARTICLE1.getId().getValue())
			.size().isEqualTo(2);
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql",
			"ArticlePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void getArticleList_withAuthorName_succeeds() {
		// when
		List<Article> articles =
			adapter.loadArticleList(PAGING_PARAM, null, USER1.getUsername(), null);

		// then
		assertThat(articles.stream().map(Article::getId)).contains(ARTICLE1.getId()).size().isEqualTo(1);
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql",
			"ArticlePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void getArticleList_withFavoritedName_succeeds() {
		// when
		List<Article> articles =
			adapter.loadArticleList(PAGING_PARAM, null, null, USER1.getUsername());

		// then
		assertThat(articles.stream().map(Article::getId).map(ArticleId::getValue))
			.contains(ARTICLE1.getId().getValue(),ARTICLE2.getId().getValue())
			.size().isEqualTo(2);
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql",
			"ArticlePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void getFeedArticleList_succeeds() {
		// when
		List<Article> articles =
			adapter.loadFeedArticleList(PAGING_PARAM, USER1.getId());

		// then
		assertThat(articles.stream().map(Article::getId).map(ArticleId::getValue))
			.contains(ARTICLE2.getId().getValue())
			.size().isEqualTo(1);
	}

	@Sql(
		value = {
			"UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql",
			"ArticlePersistenceAdapterTest.sql"
		},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void getAllTags_succeeds() {
		// when
		final Set<String> allTags = adapter.getAllTags();

		// then
		assertThat(allTags).contains("user1", "user2").size().isEqualTo(2);
	}

}

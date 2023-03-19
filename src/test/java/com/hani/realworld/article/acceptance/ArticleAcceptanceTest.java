package com.hani.realworld.article.acceptance;

import static com.hani.realworld.article.adapter.in.dto.response.ArticleResponse.*;
import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;
import static org.springframework.test.annotation.DirtiesContext.*;

import java.util.List;
import java.util.stream.Stream;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.test.annotation.DirtiesContext;

import com.hani.realworld.AcceptanceTest;
import com.hani.realworld.article.adapter.in.dto.request.UpdateArticleRequest;
import com.hani.realworld.article.adapter.in.dto.response.ArticleResponse;
import com.hani.realworld.article.adapter.in.dto.response.MultipleArticleResponse;
import com.hani.realworld.user.adapter.in.web.dto.response.ProfileResponse;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ArticleAcceptanceTest extends AcceptanceTest {

	@TestFactory
	Stream<DynamicTest> manageArticle() {
		return Stream.of(
			dynamicTest("게시글 두 개 생성", () -> {
				createArticle(ARTICLE1);
				createArticle(ARTICLE2);
			}),
			dynamicTest("게시글 단일 조회", () -> {
				String uri = "/api/articles/" + ARTICLE1.getSlug().getSlug();
				ArticleResponse response = get(uri, HttpStatus.SC_OK,
					ArticleResponse.class);
				ArticleInfo article = response.getArticle();
				assertThat(article.getSlug()).isEqualTo(ARTICLE1.getSlug().getSlug());
			}),
			dynamicTest("게시글 전체 조회", () -> {
				MultipleArticleResponse response = get("/api/articles", HttpStatus.SC_OK,
					MultipleArticleResponse.class);
				List<ArticleInfo> articles = response.getArticles();
				assertThat(response.getArticlesCount()).isEqualTo(2);
				assertCreatedAtDesc(articles);
			}),
			dynamicTest("게시글 작성자 전체 조회", () -> {
				String uri = "/api/articles?author=" + USER1.getUsername();
				MultipleArticleResponse response = get(uri, HttpStatus.SC_OK,
					MultipleArticleResponse.class);
				List<ArticleInfo> articles = response.getArticles();
				assertAuthor(articles, USER1.getUsername());
				assertThat(response.getArticlesCount()).isEqualTo(2);
				assertCreatedAtDesc(articles);
			}),
			dynamicTest("게시글 태그 전체 조회", () -> {
				String uri = "/api/articles?tag=user2";
				MultipleArticleResponse response = get(uri, HttpStatus.SC_OK,
					MultipleArticleResponse.class);
				List<ArticleInfo> articles = response.getArticles();
				assertTag(articles, "user2");
				assertThat(response.getArticlesCount()).isEqualTo(1);
				assertCreatedAtDesc(articles);
			}),
			dynamicTest("게시글 찜하기", () -> {
				String uri = "/api/articles/" + ARTICLE1.getSlug().getSlug() + "/favorite";
				ArticleResponse response = post(uri, "", token, HttpStatus.SC_OK,
					ArticleResponse.class);
				ArticleInfo article = response.getArticle();
				assertThat(article.getFavoritesCount()).isOne();
				assertThat(article.isFavorited()).isTrue();
			}),
			dynamicTest("찜한 게시글 전체 조회", () -> {
				String uri = "/api/articles?favorited=" + USER1.getUsername();
				MultipleArticleResponse response = get(uri, HttpStatus.SC_OK,
					MultipleArticleResponse.class);
				assertThat(response.getArticlesCount()).isOne();
				assertCreatedAtDesc(response.getArticles());
			}),
			dynamicTest("게시글 찜하기 취소", () -> {
				String uri = "/api/articles/" + ARTICLE1.getSlug().getSlug() + "/favorite";
				ArticleResponse response = delete(uri, token, HttpStatus.SC_OK,
					ArticleResponse.class);
				ArticleInfo article = response.getArticle();
				assertThat(article.getFavoritesCount()).isZero();
				assertThat(article.isFavorited()).isFalse();
			}),
			dynamicTest("게시글 수정", () -> {
				String uri = "/api/articles/" + ARTICLE2.getSlug().getSlug();
				UpdateArticleRequest request = new UpdateArticleRequest("How to train your dragoon", null, null);
				String body = objectMapper.writeValueAsString(request);
				ArticleResponse response = put(uri, body, token, HttpStatus.SC_OK,
					ArticleResponse.class);
				assertThat(response.getArticle().getTitle()).isEqualTo(request.getTitle());
			}),
			dynamicTest("게시글 삭제", () -> {
				delete("/api/articles/" + ARTICLE1.getSlug().getSlug(), token);
			}),
			dynamicTest("피드 조회", () -> {
				String user2Token = registerAndLogin(USER2, "password2");
				post("/api/profiles/" + USER1.getUsername() + "/follow", "", user2Token,
					HttpStatus.SC_OK, ProfileResponse.class);
				MultipleArticleResponse response = get("/api/articles/feed", user2Token,
					HttpStatus.SC_OK, MultipleArticleResponse.class);
				List<ArticleInfo> articles = response.getArticles();
				assertThat(response.getArticlesCount()).isEqualTo(1);
				assertFollowed(articles);
				assertCreatedAtDesc(articles);
			})
		);
	}

	private void assertCreatedAtDesc(List<ArticleInfo> articles) {
		for (int i = 0; i < articles.size() - 1; i++) {
			assertThat(articles.get(i).getCreatedAt()).isAfter(articles.get(i + 1).getCreatedAt());
		}
	}

	private void assertAuthor(List<ArticleInfo> articles, String author) {
		for (ArticleInfo article : articles) {
			assertThat(article.getAuthor().getUsername()).isEqualTo(author);
		}
	}

	private void assertTag(List<ArticleInfo> articles, String tag) {
		for (ArticleInfo article : articles) {
			assertThat(article.getTagList()).contains(tag);
		}
	}

	private void assertFollowed(List<ArticleInfo> articles) {
		for (ArticleInfo article : articles) {
			assertThat(article.getAuthor().isFollowing()).isTrue();
		}
	}
}

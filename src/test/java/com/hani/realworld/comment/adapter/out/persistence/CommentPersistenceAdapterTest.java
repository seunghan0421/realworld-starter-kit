package com.hani.realworld.comment.adapter.out.persistence;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static com.hani.realworld.common.fixture.CommentFixture.*;
import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.hani.realworld.article.adapter.out.persistence.ArticleJpaEntity;
import com.hani.realworld.article.adapter.out.persistence.ArticleMapper;
import com.hani.realworld.article.adapter.out.persistence.ArticlePersistenceAdapter;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.comment.domain.Comment;
import com.hani.realworld.user.adapter.out.persistence.UserMapper;
import com.hani.realworld.user.adapter.out.persistence.UserPersistenceAdapter;
import com.hani.realworld.user.domain.Profile;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
	ArticlePersistenceAdapter.class, ArticleMapper.class,
	UserPersistenceAdapter.class, UserMapper.class,
	CommentPersistenceAdapter.class, CommentMapper.class
})
class CommentPersistenceAdapterTest {

	@Autowired
	private CommentPersistenceAdapter adapter;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	void addComment_succeeds() {
		// given
		Comment comment = defaultComment()
			.withId(null)
			.withArticleId(ARTICLE1.getId().getValue())
			.withAuthor(PROFILE1)
			.withBody(COMMENT1.getBody())
			.build();

		// when
		adapter.createComment(comment);

		// then
		assertThat(commentRepository.count()).isEqualTo(1);

		CommentJpaEntity savedEntity = commentRepository.findAll().get(0);

		assertThat(savedEntity.getId()).isEqualTo(1L);
		assertThat(savedEntity.getArticleId()).isEqualTo(ARTICLE1.getId().getValue());
		assertThat(savedEntity.getAuthorId()).isEqualTo(PROFILE1.getId().getValue());
		assertThat(savedEntity.getBody()).isEqualTo(COMMENT1.getBody());
		assertThat(savedEntity.getCreatedAt()).isNotNull();
		assertThat(savedEntity.getCreatedAt()).isNotNull();
	}

	@Sql(
		value = {"CommentPersistenceAdapterTest.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void delete_Article_succeeds() {
		// when
		adapter.deleteComment(COMMENT1.getId());

		// then
		List<CommentJpaEntity> commentJpaEntityList = commentRepository.findAll();
		assertThat(commentJpaEntityList).size().isEqualTo(1);
		assertThat(commentJpaEntityList.get(0).getId()).isEqualTo(2L);
	}

	@Sql(
		value = {"CommentPersistenceAdapterTest.sql", "UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void getComment_succeeds() {
		// when
		Comment comment = adapter.getComment(COMMENT1.getId());

		// then
		assertThat(comment.getId()).isEqualTo(COMMENT1.getId());
		assertThat(comment.getAuthor().getId()).isEqualTo(PROFILE1.getId());
		assertThat(comment.getBody()).isEqualTo(COMMENT1.getBody());
	}

	@Sql(
		value = {"CommentPersistenceAdapterTest.sql", "UserPersistenceAdapterTest.sql",
			"ProfilePersistenceAdapterTest.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void getCommentWithArticleId_succeeds() {
		// when
		List<Comment> comments = adapter.getCommentsWithArticleId(ARTICLE1.getId());

		// then
		assertThat(comments).size().isEqualTo(2);
		assertThat(comments.stream().map(Comment::getId)).contains(COMMENT1.getId(), COMMENT2.getId());
		assertThat(comments.stream().map(Comment::getAuthor).map(Profile::getId))
			.contains(PROFILE1.getId(), PROFILE2.getId());
	}

}

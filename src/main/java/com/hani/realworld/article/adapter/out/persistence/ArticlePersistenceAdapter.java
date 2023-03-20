package com.hani.realworld.article.adapter.out.persistence;

import static com.hani.realworld.article.domain.Article.*;
import static com.hani.realworld.user.domain.Profile.*;
import static com.hani.realworld.user.domain.User.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.hani.realworld.article.application.port.in.command.PagingParam;
import com.hani.realworld.article.application.port.out.CreateArticleStatePort;
import com.hani.realworld.article.application.port.out.DeleteArticleWithArticleIdPort;
import com.hani.realworld.article.application.port.out.GetAllTagsPort;
import com.hani.realworld.article.application.port.out.LoadArticleListWithParamsPort;
import com.hani.realworld.article.application.port.out.LoadArticleWithSlugPort;
import com.hani.realworld.article.application.port.out.LoadFeedArticleListPort;
import com.hani.realworld.article.application.port.out.UpdateArticleStatePort;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.common.annotation.PersistenceAdapter;
import com.hani.realworld.common.exception.article.ArticleNotFoundException;
import com.hani.realworld.user.application.port.out.LoadProfileWithProfileIdPort;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsernamePort;
import com.hani.realworld.user.application.port.out.LoadUserWithUsernamePort;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ArticlePersistenceAdapter implements
	CreateArticleStatePort,
	LoadArticleWithSlugPort,
	LoadArticleListWithParamsPort,
	LoadFeedArticleListPort,
	UpdateArticleStatePort,
	DeleteArticleWithArticleIdPort,
	GetAllTagsPort {

	private final LoadUserWithUsernamePort loadUserWithUsernamePort;
	private final LoadProfileWithProfileIdPort loadProfileWithProfileIdPort;
	private final LoadProfileWithUserIdPort loadProfileWithUserIdPort;
	private final LoadProfileWithUsernamePort loadProfileWithUsernamePort;

	private final ArticleRepository articleRepository;
	private final ArticleQueryRepository articleQueryRepository;
	private final ArticleMapper articleMapper;

	@Override
	public void create(Article article) {
		ArticleJpaEntity articleJpaEntity = articleMapper.mapToArticleJpaEntity(article);

		articleRepository.save(articleJpaEntity);
	}

	@Override
	public Article load(String slug) {
		ArticleJpaEntity articleJpaEntity = articleRepository.getArticleWithSlug(slug)
			.orElseThrow(ArticleNotFoundException::new);
		Profile author = loadProfileWithProfileIdPort.
			loadProfileWithProfileId(new ProfileId(articleJpaEntity.getAuthorId()));

		return articleMapper.mapToArticleEntity(articleJpaEntity, author);
	}

	@Override
	public void update(Article article) {
		ArticleJpaEntity articleJpaEntity = articleRepository.findById(article.getId().getValue())
			.orElseThrow(ArticleNotFoundException::new);

		articleJpaEntity.update(article);
	}

	@Override
	public void delete(ArticleId articleId) {
		articleRepository.deleteById(articleId.getValue());
	}

	@Override
	public Set<String> getAllTags() {
		return Collections.unmodifiableSet(articleRepository.getAllTags());
	}

	@Override
	public List<Article> loadArticleList(
		PagingParam pagingParam,
		String tagName,
		String authorName,
		String favoritedName) {

		ProfileId authorId = parseProfileId(authorName);
		UserId favoritedId = parseUserId(favoritedName);

		return addProfileAndParseToArticle(
			articleQueryRepository.findAllWithParams(pagingParam, authorId, favoritedId, tagName));
	}

	private ProfileId parseProfileId(String authorName) {
		try {
			return Optional.ofNullable(authorName)
				.map(loadProfileWithUsernamePort::loadProfileWithUsername)
				.map(Profile::getId)
				.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	private UserId parseUserId(String favoritedName) {
		try {
			return Optional.ofNullable(favoritedName)
				.map(loadUserWithUsernamePort::loadUserWithUsername)
				.map(User::getId)
				.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Article> loadFeedArticleList(PagingParam pagingParam, UserId userId) {
		Profile profile = loadProfileWithUserIdPort.loadProfileWithUserId(userId);

		List<Long> authorIds = profile.getFollowees().getFollowees().stream()
			.map(UserId::getValue)
			.collect(Collectors.toList());

		return addProfileAndParseToArticle(articleQueryRepository.findFeedArticles(pagingParam, authorIds));
	}

	private List<Article> addProfileAndParseToArticle(List<ArticleJpaEntity> articleJpaEntities) {
		return articleJpaEntities.stream()
			.map(jpaEntity -> {
				Profile author = loadProfileWithProfileIdPort.
					loadProfileWithProfileId(new ProfileId(jpaEntity.getAuthorId()));

				return articleMapper.mapToArticleEntity(jpaEntity, author);
			})
			.collect(Collectors.toList());

	}

}

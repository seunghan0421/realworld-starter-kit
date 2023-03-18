package com.hani.realworld.article.adapter.out.persistence;

import static com.hani.realworld.article.domain.Article.*;
import static com.hani.realworld.user.domain.Profile.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

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
import com.hani.realworld.user.application.port.out.LoadProfileWithProfileIdPort;
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

	private final ArticleRepository articleRepository;
	private final LoadProfileWithProfileIdPort loadProfileWithProfileIdPort;
	private final ArticleMapper articleMapper;

	@Override
	public void create(Article article) {
		ArticleJpaEntity articleJpaEntity = articleMapper.mapToArticleJpaEntity(article);

		articleRepository.save(articleJpaEntity);
	}

	@Override
	public Article load(String slug) {
		ArticleJpaEntity articleJpaEntity = articleRepository.getArticleWithSlug(slug)
			.orElseThrow(EntityNotFoundException::new);
		Profile author = loadProfileWithProfileIdPort.
			loadProfileWithProfileId(new ProfileId(articleJpaEntity.getAuthorId()));

		return articleMapper.mapToArticleEntity(articleJpaEntity, author);
	}

	@Override
	public void update(Article article) {
		ArticleJpaEntity articleJpaEntity = articleRepository.findById(article.getId().getValue())
			.orElseThrow(EntityNotFoundException::new);

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
	public List<Article> loadArticleList(PagingParam pagingParam, String tag, String author, String favorited) {
		return null;
	}

	@Override
	public List<Article> loadFeedArticleList(PagingParam pagingParam, User.UserId userId) {
		return null;
	}
}

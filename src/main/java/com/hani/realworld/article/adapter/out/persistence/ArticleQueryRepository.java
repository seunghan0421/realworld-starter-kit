package com.hani.realworld.article.adapter.out.persistence;

import static com.hani.realworld.article.adapter.out.persistence.QArticleJpaEntity.*;
import static com.hani.realworld.user.domain.Profile.*;
import static com.hani.realworld.user.domain.User.*;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.hani.realworld.article.application.port.in.command.PagingParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ArticleQueryRepository {

	private final JPAQueryFactory queryFactory;

	public List<ArticleJpaEntity> findAllWithParams(
		PagingParam pagingParam,
		ProfileId authorId, UserId
		favoritedId,
		String tagName) {
		return queryFactory.selectDistinct(articleJpaEntity)
			.from(articleJpaEntity)
			.leftJoin(articleJpaEntity.tags)
			.leftJoin(articleJpaEntity.users)
			.where(
				eqAuthor(authorId),
				eqFavorite(favoritedId),
				eqTag(tagName)
			)
			.limit(pagingParam.getLimit())
			.offset(pagingParam.getOffset())
			.orderBy(articleJpaEntity.createdAt.desc())
			.fetch();
	}

	public List<ArticleJpaEntity> findFeedArticles(PagingParam pagingParam, List<Long> followIds) {
		if (followIds.size() == 0) {
			return Collections.emptyList();
		}

		return queryFactory.selectDistinct(articleJpaEntity)
			.from(articleJpaEntity)
			.where(inAuthorIds(followIds))
			.limit(pagingParam.getLimit())
			.offset(pagingParam.getOffset())
			.orderBy(articleJpaEntity.createdAt.desc())
			.fetch();
	}

	private BooleanExpression eqAuthor(ProfileId authorId) {
		return authorId == null ? null : articleJpaEntity.authorId.eq(authorId.getValue());
	}

	private BooleanExpression eqFavorite(UserId favoritedId) {
		return favoritedId == null ? null : articleJpaEntity.users.contains(favoritedId.getValue());
	}

	private BooleanExpression eqTag(String tagName) {
		return StringUtils.isBlank(tagName) ? null : articleJpaEntity.tags.contains(tagName);
	}

	private BooleanExpression inAuthorIds(List<Long> authorIds) {
		return authorIds.isEmpty() ? null : articleJpaEntity.authorId.in(authorIds);
	}
}

package com.hani.realworld.common.exception.article;

import static com.hani.realworld.common.exception.ErrorCode.*;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class ArticleNotFoundException extends BusinessException {

	private static final ErrorCode ERROR_CODE = ARTICLE_ENTITY_NOT_FOUND;

	public ArticleNotFoundException() {
		super(ERROR_CODE);
	}

}

package com.hani.realworld.common.exception.article;

import static com.hani.realworld.common.exception.ErrorCode.*;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class NotFavoritedArticleException extends BusinessException {

	private static final ErrorCode ERROR_CODE = NOT_FAVORITED_ARTICLE;

	public NotFavoritedArticleException() {
		super(ERROR_CODE);
	}
}

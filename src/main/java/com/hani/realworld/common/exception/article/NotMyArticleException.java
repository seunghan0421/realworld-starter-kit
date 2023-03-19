package com.hani.realworld.common.exception.article;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class NotMyArticleException extends BusinessException {

	private static final ErrorCode ERROR_CODE = ErrorCode.UNAUTHORIZED_ARTICLE;

	public NotMyArticleException() {
		super(ERROR_CODE);
	}
}

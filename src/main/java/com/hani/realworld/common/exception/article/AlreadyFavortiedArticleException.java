package com.hani.realworld.common.exception.article;

import static com.hani.realworld.common.exception.ErrorCode.*;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class AlreadyFavortiedArticleException extends BusinessException {

	private static final ErrorCode ERROR_CODE = ALREADY_FAVORITED_ARTICLE;

	public AlreadyFavortiedArticleException() {
		super(ERROR_CODE);
	}

}

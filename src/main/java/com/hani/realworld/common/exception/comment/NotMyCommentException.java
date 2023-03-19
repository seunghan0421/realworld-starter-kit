package com.hani.realworld.common.exception.comment;

import static com.hani.realworld.common.exception.ErrorCode.*;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class NotMyCommentException extends BusinessException {

	private static final ErrorCode ERROR_CODE = UNAUTHORIZED_COMMENT;

	public NotMyCommentException() {
		super(ERROR_CODE);
	}

}

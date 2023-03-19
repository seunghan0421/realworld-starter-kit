package com.hani.realworld.common.exception.comment;

import static com.hani.realworld.common.exception.ErrorCode.*;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class CommentNotFoundException extends BusinessException {

	private static final ErrorCode ERROR_CODE = COMMENT_ENTITY_NOT_FOUND;

	public CommentNotFoundException() {
		super(ERROR_CODE);
	}

}

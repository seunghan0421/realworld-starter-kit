package com.hani.realworld.common.exception.infra;

import static com.hani.realworld.common.exception.ErrorCode.*;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class TokenExpiredException extends BusinessException {

	private static final ErrorCode ERROR_CODE = TOKEN_EXPIRED;

	public TokenExpiredException() {
		super(ERROR_CODE);
	}
}

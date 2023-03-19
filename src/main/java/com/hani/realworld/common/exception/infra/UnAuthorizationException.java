package com.hani.realworld.common.exception.infra;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class UnAuthorizationException extends BusinessException {

	private static final ErrorCode ERROR_CODE = ErrorCode.UNAUTORIZED;

	public UnAuthorizationException() {
		super(ERROR_CODE);
	}
}

package com.hani.realworld.common.exception;

import static com.hani.realworld.common.exception.ErrorCode.*;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;

	public BusinessException() {
		this(BUSINESS_EXCEPTION);
	}

	public BusinessException(ErrorCode code) {
		super();

		this.errorCode = code;
	}
}

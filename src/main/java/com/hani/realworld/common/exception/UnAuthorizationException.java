package com.hani.realworld.common.exception;

public class UnAuthorizationException extends RuntimeException {

	private static final String MESSAGE = "인증되지 않은 사용자입니다.";

	public UnAuthorizationException() {
		super(MESSAGE);
	}

	public UnAuthorizationException(String message) {
		super(message);
	}
}

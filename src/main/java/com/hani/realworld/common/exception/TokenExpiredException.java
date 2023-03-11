package com.hani.realworld.common.exception;

public class TokenExpiredException extends RuntimeException {

	private static final String MESSAGE = "Json Web Token이 만료되었습니다.";

	public TokenExpiredException() {
		super(MESSAGE);
	}

	public TokenExpiredException(String message) {
		super(message);
	}
}

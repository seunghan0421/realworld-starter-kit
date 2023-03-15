package com.hani.realworld.common.exception;

public class NotMyArticleException extends RuntimeException{

	private static final String MESSAGE = "article을 작성한 사용자가 아닙니다..";

	public NotMyArticleException() {
		super(MESSAGE);
	}

	public NotMyArticleException(String message) {
		super(message);
	}
}

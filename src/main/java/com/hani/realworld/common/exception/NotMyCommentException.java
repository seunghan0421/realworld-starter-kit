package com.hani.realworld.common.exception;

public class NotMyCommentException extends RuntimeException{

	private static final String MESSAGE = "comment를 작성한 사용자가 아닙니다.";

	public NotMyCommentException() {
		super(MESSAGE);
	}

	public NotMyCommentException(String message) {
		super(message);
	}
}

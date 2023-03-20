package com.hani.realworld.common.exception;

import lombok.Getter;

// TODO: Exceptoion Status에 대해서 좀 더 공부하기
@Getter
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
	METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
	HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
	INTERNAL_SERVER_ERROR(500, "C007" , "Internal Server Error" ),
	BUSINESS_EXCEPTION(500, "C999", "Business Excption"),

	// infra
	TOKEN_EXPIRED(400, "I001", "your token is expired"),
	UNAUTORIZED(401, "I002", "you are not authorized"),

	// User
	USER_ENTITY_NOT_FOUND(204, "U001", "user entity is not found"),
	PROFILE_ENTITY_NOT_FOUND(204, "U002", "profile entity is not found"),
	ALREADY_FOLLOW_PROFILE(409, "U003", "you already follow the profile"),
	NOT_FOLLOWED_PROFILE(409, "U004", "you did not follow the profile"),

	// Article
	ARTICLE_ENTITY_NOT_FOUND(204, "A001", "article entity is not found"),
	ALREADY_FAVORITED_ARTICLE(409, "A002", "you already favorited the article"),
	NOT_FAVORITED_ARTICLE(409, "A003", "you did not favorited the article"),
	UNAUTHORIZED_ARTICLE(403, "A004", "you are not owner of article"),

	// Comment
	COMMENT_ENTITY_NOT_FOUND(204, "CM001", "comment entity is not found"),
	UNAUTHORIZED_COMMENT(403, "CM002", "you are not owner of comment");


	private final String code;
	private final String message;
	private int status;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}
}

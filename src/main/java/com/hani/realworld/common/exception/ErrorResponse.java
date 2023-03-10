package com.hani.realworld.common.exception;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * TODO: Exception 커스터마이징 하기
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorResponse {

	private String message;
	private int status;
	private List<FieldError> errors;
	private String code;

	public static ErrorResponse of(ErrorCode code) {
		return new ErrorResponse(code.getMessage(),
			code.getStatus(),
			null,
			code.getCode());
	}

	public static ErrorResponse of(Exception e) {
		return new ErrorResponse(e.getMessage(),
			0,
			null,
			"DDD");
	}

	// 이것도 커스터마이징
	public static ErrorResponse of(ErrorCode code, Set<ConstraintViolation<?>> constraintViolations) {
		return new ErrorResponse(code.getMessage(),
			code.getStatus(),
			null,
			code.getCode());
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class FieldError {
		private String field;
		private String value;
		private String reason;
	}
}

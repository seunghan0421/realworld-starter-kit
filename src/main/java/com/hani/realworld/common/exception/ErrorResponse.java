package com.hani.realworld.common.exception;

import static com.hani.realworld.common.exception.ErrorCode.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorResponse {

	private String message;
	private int status;
	private List<FieldError> errors;
	private String code;

	public static ErrorResponse of(ErrorCode code) {
		return new ErrorResponse(code.getMessage(), code.getStatus(), null, code.getCode());
	}

	public static ErrorResponse of(Exception e) {
		ErrorCode code = METHOD_NOT_ALLOWED;

		return new ErrorResponse(code.getMessage(), code.getStatus(), null, code.getCode());
	}

	public static ErrorResponse of(ErrorCode code, Set<ConstraintViolation<?>> constraintViolations) {
		List<FieldError> fieldErrors = constraintViolations.stream()
			.map(cv -> new FieldError(
				cv.getPropertyPath().toString(),
				cv.getInvalidValue().toString(),
				cv.getMessage()))
			.collect(Collectors.toList());

		return new ErrorResponse(code.getMessage(),
			code.getStatus(),
			fieldErrors,
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

package com.hani.realworld.common.exception.user;

import static com.hani.realworld.common.exception.ErrorCode.*;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

	private static final ErrorCode ERROR_CODE = USER_ENTITY_NOT_FOUND;

	public UserNotFoundException() {
		super(ERROR_CODE);
	}

}

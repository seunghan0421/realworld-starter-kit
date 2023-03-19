package com.hani.realworld.common.exception.user;

import static com.hani.realworld.common.exception.ErrorCode.*;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class AlreadyFollowProfileException extends BusinessException {

	private static final ErrorCode ERROR_CODE = ALREADY_FOLLOW_PROFILE;

	public AlreadyFollowProfileException() {
		super(ERROR_CODE);
	}

}

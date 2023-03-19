package com.hani.realworld.common.exception.user;

import static com.hani.realworld.common.exception.ErrorCode.*;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class NotFollowedProfileException extends BusinessException {

	private static final ErrorCode ERROR_CODE = NOT_FOLLOWED_PROFILE;

	public NotFollowedProfileException() {
		super(ERROR_CODE);
	}

}

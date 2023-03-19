package com.hani.realworld.common.exception.user;

import static com.hani.realworld.common.exception.ErrorCode.*;

import com.hani.realworld.common.exception.BusinessException;
import com.hani.realworld.common.exception.ErrorCode;

public class ProfileNotFoundException extends BusinessException {

	private static final ErrorCode ERROR_CODE = PROFILE_ENTITY_NOT_FOUND;

	public ProfileNotFoundException() {
		super(ERROR_CODE);
	}

}

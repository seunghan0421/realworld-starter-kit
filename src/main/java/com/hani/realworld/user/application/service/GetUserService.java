package com.hani.realworld.user.application.service;

import static com.hani.realworld.user.domain.User.*;

import com.hani.realworld.common.annotation.Query;
import com.hani.realworld.user.application.port.in.GetUserQuery;
import com.hani.realworld.user.application.port.in.result.UserResult;
import com.hani.realworld.user.application.port.out.LoadUserWithIdPort;

import lombok.RequiredArgsConstructor;

@Query
@RequiredArgsConstructor
public class GetUserService implements GetUserQuery {

	private final LoadUserWithIdPort loadUserWithIdPort;

	@Override
	public UserResult getUser(Long userId) {
		return UserResult.of(loadUserWithIdPort.loadUserWithId(new UserId(userId)));
	}
}

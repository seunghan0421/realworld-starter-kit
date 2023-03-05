package com.hani.realworld.profile.application.service;

import static com.hani.realworld.profile.domain.User.*;

import com.hani.realworld.common.annotation.Query;
import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.profile.application.port.in.GetUserQuery;
import com.hani.realworld.profile.application.port.in.result.UserResult;
import com.hani.realworld.profile.application.port.out.LoadUserWithIdPort;

import lombok.RequiredArgsConstructor;

@Query
@RequiredArgsConstructor
public class GetUserService implements GetUserQuery {

	private final LoadUserWithIdPort loadUserWithIdPort;

	@Override
	public UserResult getUser(UserId userId) {
		return UserResult.of(loadUserWithIdPort.loadUser(userId));
	}
}

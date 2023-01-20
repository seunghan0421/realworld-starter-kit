package com.hani.realworld.profile.application.service;

import static com.hani.realworld.profile.domain.User.*;

import javax.transaction.Transactional;

import com.hani.realworld.common.annotation.UseCase;
import com.hani.realworld.profile.application.port.in.GetUserQuery;
import com.hani.realworld.profile.application.port.in.result.UserResult;
import com.hani.realworld.profile.application.port.out.LoadUserPort;
import com.hani.realworld.profile.domain.User;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetUserService implements GetUserQuery {

	private final LoadUserPort loadUserPort;

	@Override
	public UserResult getUser(UserId userId) {
		return UserResult.of(loadUserPort.loadUser(userId));
	}
}

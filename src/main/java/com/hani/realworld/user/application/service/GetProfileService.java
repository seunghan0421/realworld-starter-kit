package com.hani.realworld.user.application.service;

import java.util.Optional;

import com.hani.realworld.common.annotation.Query;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Query
public class GetProfileService implements GetProfileQuery {

	@Override
	public ProfileResult getProfile(String username, Optional<Long> userId) {
		return null;
	}
}

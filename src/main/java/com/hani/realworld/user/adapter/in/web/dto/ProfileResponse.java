package com.hani.realworld.user.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hani.realworld.user.application.port.in.result.ProfileResult;
import com.hani.realworld.user.application.port.in.result.UserResult;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("profile")
public class ProfileResponse {
	private String username;
	private String bio;
	private String image;
	private boolean following;

	public static ProfileResponse of(ProfileResult result) {
		return new ProfileResponse(
			result.getUsername(),
			result.getBio(),
			result.getImage(),
			result.isFollowing()
		);
	}
}

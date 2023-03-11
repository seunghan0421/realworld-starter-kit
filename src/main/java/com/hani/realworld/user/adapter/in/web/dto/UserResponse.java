package com.hani.realworld.user.adapter.in.web.dto;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.*;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hani.realworld.user.application.port.in.result.UserResult;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@JsonTypeName("user")
public class UserResponse {
	private String email;
	private String token;
	private String username;
	private String bio;
	private String image;

	public static UserResponse of(UserResult userResult, String token) {
		return new UserResponse(
			userResult.getEmail(),
			token,
			userResult.getUsername(),
			userResult.getBio(),
			userResult.getImage());
	}

}

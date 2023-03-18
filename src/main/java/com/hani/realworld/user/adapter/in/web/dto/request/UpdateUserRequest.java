package com.hani.realworld.user.adapter.in.web.dto.request;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.*;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.*;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("user")
public class UpdateUserRequest {
	private String email;
	private String username;
	private String password;
	private String image;
	private String bio;
}

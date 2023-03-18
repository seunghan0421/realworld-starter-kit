package com.hani.realworld.user.adapter.in.web.dto.response;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.*;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.*;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("user")
public class RegisterUserRequest {
	private String username;
	private String email;
	private String password;
}

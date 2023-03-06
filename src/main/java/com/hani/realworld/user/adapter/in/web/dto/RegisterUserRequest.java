package com.hani.realworld.user.adapter.in.web.dto;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.*;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.*;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.Value;

@JsonRootName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
public class RegisterUserRequest {
	private String username;
	private String email;
	private String password;
}

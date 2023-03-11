package com.hani.realworld.infra.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginToken {
	private final long id;
	private final String token;
	private final String email;

}

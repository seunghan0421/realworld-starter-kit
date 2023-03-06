package com.hani.realworld.infra.jwt;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class SecurityUser extends User {

	private final Long id;
	private final String email;
	private final String token;

	private SecurityUser(
		Long id,
		String email,
		String token,
		Collection<? extends GrantedAuthority> authorities) {
		super(email, token, authorities);

		this.id = id;
		this.email = email;
		this.token = token;
	}

	public static SecurityUser of(
		Long id,
		String email,
		String token,
		String... authorities) {
		return new SecurityUser(id, email, token, AuthorityUtils.createAuthorityList(authorities));
	}

	public static <R> R defaultIfNull(
		SecurityUser securityUser,
		Function<SecurityUser, R> securityUserResolver,
		final R defaultValue) {
		return Optional.ofNullable(securityUser)
			.map(securityUserResolver)
			.orElse(defaultValue);
	}

}

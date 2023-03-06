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
	private final String username;
	private final String email;
	private final String password;

	private SecurityUser(
		Long id,
		String username,
		String email,
		String password,
		Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);

		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public static SecurityUser of(
		Long id,
		String username,
		String email,
		String password,
		String... authorities) {
		return new SecurityUser(id, username, email, password, AuthorityUtils.createAuthorityList(authorities));
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

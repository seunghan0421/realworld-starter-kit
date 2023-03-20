package com.hani.realworld.infra.jwt;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final FilterChain filterChain) throws IOException, ServletException {

		jwtProvider.extract(request.getHeader(AUTHORIZATION_HEADER))
			.ifPresent(token -> {
				String email = jwtProvider.parseSubject(token);

				Authentication authentication =
					new UsernamePasswordAuthenticationToken(email, token, Collections.emptyList());

				SecurityContextHolder.getContext().setAuthentication(authentication);
			});

		filterChain.doFilter(request, response);
	}

}

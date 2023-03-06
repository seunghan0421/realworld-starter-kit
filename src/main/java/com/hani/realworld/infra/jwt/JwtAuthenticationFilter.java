package com.hani.realworld.infra.jwt;

import java.io.IOException;
import java.util.Objects;

import io.jsonwebtoken.Claims;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hani.realworld.common.util.Tokens;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final UserDetailsService customUserDetailsService;
	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final FilterChain filterChain) throws IOException, ServletException {

		try {
			final String token = Tokens.get(request);

			if (Tokens.isBlankToken(token)) {
				filterChain.doFilter(request, response);
				return;
			}

			final String email = jwtProvider.getClaims(token, Claims::getId);
			final UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

			if (isNotExistsUserDetails(userDetails)) {
				throw new UsernameNotFoundException("유효하지 않은 인증정보 입니다.");
			}

			new AccountStatusUserDetailsChecker().check(userDetails);

			final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private boolean isNotExistsUserDetails(final UserDetails userDetails) {
		return Objects.isNull(userDetails);
	}
}

package com.hani.realworld.infra.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.hani.realworld.common.exception.TokenExpiredException;
import com.hani.realworld.common.exception.UnAuthorizationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {

	public static final String HEADER_PREFIX = "Token ";

	// TODO Bean으로 주입해야함
	private final JwtProperties jwtProperty;

	public String generate(String email) {
		LocalDateTime currentTime = LocalDateTime.now();
		return Jwts.builder()
			.setSubject(email)
			.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
			.setExpiration(
				Date.from(
					currentTime.plusMinutes(jwtProperty.getExpiration()).atZone(ZoneId.systemDefault()).toInstant()))
			.signWith(SignatureAlgorithm.HS512, jwtProperty.getSecretKey())
			.compact();
	}

	public Optional<String> extract(String header) {
		if (header == null) {
			return Optional.empty();
		}

		if (header.length() < HEADER_PREFIX.length()) {
			throw new AuthenticationServiceException("Invalid authorization header size.");
		}

		return Optional.of(header.substring(HEADER_PREFIX.length()));
	}

	public String parseSubject(String token) {
		return parseClaims(token).getBody().getSubject();
	}

	private Jws<Claims> parseClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(jwtProperty.getSecretKey()).parseClaimsJws(token);
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw new BadCredentialsException("Invalid Json Web Token");
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException("Expired Json Web Token");
		}
	}

}

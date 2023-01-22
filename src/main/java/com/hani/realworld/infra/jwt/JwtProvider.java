package com.hani.realworld.infra.jwt;

import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.hani.realworld.common.exception.UnAuthorizationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {

	// TODO Bean으로 주입해야함
	private final JwtProperties jwtProperty = new JwtProperties("realworld", 600000);

	public String generate(final String email, final String username) {
		final Date now = new Date();
		final Date expiration = new Date(now.getTime() + jwtProperty.getExpiration());

		return Jwts.builder()
			.setSubject("Real World API Token")
			.setIssuer("Real World API")
			.setIssuedAt(now)
			.setId(email)
			.setAudience(username)
			.setExpiration(expiration)
			.signWith(SignatureAlgorithm.HS256, jwtProperty.getSecretKey())
			.compact();
	}

	public <R> R getClaims(final String token, Function<Claims, R> claimsResolver) {
		final Claims claims = parseClaimsJws(token);
		return claimsResolver.apply(claims);
	}

	private Claims parseClaimsJws(final String token) {
		try {
			final String secretKey = jwtProperty.getSecretKey();
			return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			throw new UnAuthorizationException("인증이 만료되었습니다.");
		} catch (UnsupportedJwtException e) {
			throw new UnAuthorizationException("지원하지 않는 형식입니다.");
		} catch (MalformedJwtException e) {
			throw new UnAuthorizationException("잘못된 형식이 포함 되었습니다.");
		} catch (SignatureException e) {
			throw new UnAuthorizationException("잘못된 서명입니다.");
		} catch (IllegalArgumentException e) {
			throw new UnAuthorizationException("유효하지 않은 값이 포함 되었습니다.");
		}
	}
}

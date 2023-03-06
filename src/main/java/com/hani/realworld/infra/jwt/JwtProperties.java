package com.hani.realworld.infra.jwt;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import com.hani.realworld.common.util.SelfValidating;

import lombok.Getter;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "config.jwt")
public class JwtProperties extends SelfValidating<JwtProperties> {

	@NotBlank(message = "JWT 암호를 설정해주세요.")
	private final String secretKey;

	@Min(value = 0, message = "JWT 만료시간을 설정해주세요.")
	private final long expiration;

	public JwtProperties(String secretKey, long expiration) {
		this.secretKey = secretKey;
		this.expiration = expiration;

		this.validateSelf();
	}
}

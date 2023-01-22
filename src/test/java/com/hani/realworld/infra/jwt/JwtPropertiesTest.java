package com.hani.realworld.infra.jwt;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtPropertiesTest {

	@Autowired
	JwtProperties jwtProperties;

	@Test
	void properties_binding_Succeeds() {
		assertThat(jwtProperties.getSecretKey()).isNotBlank();
		assertThat(jwtProperties.getExpiration()).isGreaterThan(0);
	}
}

package com.hani.realworld.infra.jwt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {JwtProperties.class})
public class JwtConfiguration {
}

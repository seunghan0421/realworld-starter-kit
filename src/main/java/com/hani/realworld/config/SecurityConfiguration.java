package com.hani.realworld.config;

import static org.springframework.http.HttpMethod.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hani.realworld.infra.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.httpBasic().disable();
		http.csrf().disable();
		http.formLogin().disable();
		http.logout().disable();
		http.cors();

		http.exceptionHandling()
			.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		// TODO: 요구사항에 맞게 Security 커스터마이징
		http.authorizeRequests()
			.antMatchers(OPTIONS).permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers(HttpMethod.POST, "/api/users", "/api/users/login").permitAll()
			.antMatchers(HttpMethod.GET, "/api/articles/feed").authenticated()
			.antMatchers(HttpMethod.GET, "/api/profiles/**", "/api/articles/**",
				"/api/tags","docs/index.html").permitAll()
			.anyRequest().authenticated();
	}
}

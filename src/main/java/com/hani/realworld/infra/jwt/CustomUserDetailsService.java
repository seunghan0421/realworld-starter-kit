package com.hani.realworld.infra.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.hani.realworld.profile.application.port.out.LoadUserWithEmailPort;
import com.hani.realworld.profile.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final LoadUserWithEmailPort loadUserWithEmailPort;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = loadUserWithEmailPort.loadUser(username);

		return SecurityUser.of(
			user.getUsername(),
			user.getEmail(),
			user.getPassword().getValue(),
			ROLE.USER.name());
	}

}

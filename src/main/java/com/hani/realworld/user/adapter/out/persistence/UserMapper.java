package com.hani.realworld.user.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.hani.realworld.user.domain.User;

@Component
public class UserMapper {

	UserJpaEntity mapToJpaEntity(User user) {
		return new UserJpaEntity(
			user.getId() == null ? null : user.getId().getValue(),
			user.getUsername(),
			user.getEmail(),
			user.getPassword().getValue(),
			user.getBio(),
			user.getImage());
	}
}

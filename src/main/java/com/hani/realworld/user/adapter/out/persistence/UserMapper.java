package com.hani.realworld.user.adapter.out.persistence;

import static com.hani.realworld.user.domain.User.*;

import org.springframework.stereotype.Component;

import com.hani.realworld.user.domain.Password;
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
			user.getImage(),
			user.getToken());
	}

	public User mapToUserEntity(UserJpaEntity userJpaEntity) {
		return withId(
			new UserId(userJpaEntity.getId()),
			userJpaEntity.getUsername(),
			userJpaEntity.getEmail(),
			new Password(userJpaEntity.getPassword()),
			userJpaEntity.getBio(),
			userJpaEntity.getImage(),
			userJpaEntity.getToken());
	}
}

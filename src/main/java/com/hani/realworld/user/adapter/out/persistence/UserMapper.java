package com.hani.realworld.user.adapter.out.persistence;

import static com.hani.realworld.user.domain.User.*;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hani.realworld.user.domain.Followees;
import com.hani.realworld.user.domain.Password;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

@Component
public class UserMapper {

	ProfileJpaEntity mapToProfileJpaEntity(Profile profile) {
		return new ProfileJpaEntity(
			profile.getId() == null ? null : profile.getId().getValue(),
			mapToUserJpaEntity(profile.getUser()),
			profile.getFollowees().getFollowees().stream()
				.map(UserId::getValue)
				.collect(Collectors.toSet()));
	}

	Profile mapToProfileEntity(ProfileJpaEntity profileJpaEntity) {
		return Profile.withId(
			new Profile.ProfileId(profileJpaEntity.getId()),
			mapToUserEntity(profileJpaEntity.getUser()),
			new Followees(profileJpaEntity.getFollowees().stream()
				.map(UserId::new)
				.collect(Collectors.toSet())));
	}

	UserJpaEntity mapToUserJpaEntity(User user) {
		return new UserJpaEntity(
			user.getId() == null ? null : user.getId().getValue(),
			user.getUsername(),
			user.getEmail(),
			user.getPassword().getValue(),
			user.getBio(),
			user.getImage());
	}

	User mapToUserEntity(UserJpaEntity userJpaEntity) {
		return withId(
			new UserId(userJpaEntity.getId()),
			userJpaEntity.getUsername(),
			userJpaEntity.getEmail(),
			new Password(userJpaEntity.getPassword()),
			userJpaEntity.getBio(),
			userJpaEntity.getImage());
	}
}

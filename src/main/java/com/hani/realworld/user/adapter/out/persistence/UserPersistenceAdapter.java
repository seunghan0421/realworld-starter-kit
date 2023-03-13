package com.hani.realworld.user.adapter.out.persistence;

import javax.persistence.EntityNotFoundException;

import com.hani.realworld.common.annotation.PersistenceAdapter;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserId;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsername;
import com.hani.realworld.user.application.port.out.LoadUserWithEmailPort;
import com.hani.realworld.user.application.port.out.LoadUserWithIdPort;
import com.hani.realworld.user.application.port.out.RegisterProfileStatePort;
import com.hani.realworld.user.application.port.out.RegisterUserStatePort;
import com.hani.realworld.user.application.port.out.UpdateProfileStatePort;
import com.hani.realworld.user.application.port.out.UpdateUserStatePort;
import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements
	LoadUserWithEmailPort,
	LoadUserWithIdPort,
	RegisterUserStatePort,
	RegisterProfileStatePort,
	UpdateUserStatePort,
	UpdateProfileStatePort,
	LoadProfileWithUsername,
	LoadProfileWithUserId {

	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final UserMapper userMapper;

	@Override
	public User loadUserWithEmail(String email) {
		UserJpaEntity userJpaEntity = userRepository.findByEmail(email)
			.orElseThrow(EntityNotFoundException::new);

		return userMapper.mapToUserEntity(userJpaEntity);
	}

	@Override
	public User loadUserWithId(User.UserId userId) {
		UserJpaEntity userJpaEntity = userRepository.findById(userId.getValue())
			.orElseThrow(EntityNotFoundException::new);

		return userMapper.mapToUserEntity(userJpaEntity);
	}

	@Override
	public void register(User user) {
		UserJpaEntity userEntity = userMapper.mapToUserJpaEntity(user);

		userRepository.save(userEntity);
	}

	@Override
	public void register(Profile profile) {
		ProfileJpaEntity profileEntity = userMapper.mapToProfileJpaEntity(profile);

		profileRepository.save(profileEntity);
	}

	@Override
	public void updateUserState(User user) {
		UserJpaEntity userJpaEntity = userRepository.findById(user.getId().getValue())
			.orElseThrow(EntityNotFoundException::new);

		userJpaEntity.update(
			user.getUsername(),
			user.getEmail(),
			user.getPassword().getValue(),
			user.getBio(),
			user.getImage());
	}

	@Override
	public void updateProfile(Profile profile) {
		ProfileJpaEntity profileJpaEntity = profileRepository.findById(profile.getId().getValue())
			.orElseThrow(EntityNotFoundException::new);

		profileJpaEntity.update(profile);
	}

	@Override
	public Profile loadProfileWithUserId(User.UserId userId) {
		ProfileJpaEntity profileJpaEntity = profileRepository.getProfileJpaEntityByUser(userId.getValue())
			.orElseThrow(EntityNotFoundException::new);

		return userMapper.mapToProfileEntity(profileJpaEntity);
	}

	@Override
	public Profile loadProfileWithUsername(String username) {
		ProfileJpaEntity profileJpaEntity = profileRepository.getProfileJpaEntityByUsername(username)
			.orElseThrow(EntityNotFoundException::new);

		return userMapper.mapToProfileEntity(profileJpaEntity);
	}

}

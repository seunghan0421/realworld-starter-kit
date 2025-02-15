package com.hani.realworld.user.adapter.out.persistence;

import static com.hani.realworld.user.domain.Profile.*;

import com.hani.realworld.article.adapter.out.persistence.ArticleRepository;
import com.hani.realworld.common.annotation.PersistenceAdapter;
import com.hani.realworld.common.exception.user.ProfileNotFoundException;
import com.hani.realworld.common.exception.user.UserNotFoundException;
import com.hani.realworld.user.application.port.out.LoadProfileWithProfileIdPort;
import com.hani.realworld.user.application.port.out.LoadProfileWithUserIdPort;
import com.hani.realworld.user.application.port.out.LoadProfileWithUsernamePort;
import com.hani.realworld.user.application.port.out.LoadUserWithEmailPort;
import com.hani.realworld.user.application.port.out.LoadUserWithIdPort;
import com.hani.realworld.user.application.port.out.LoadUserWithUsernamePort;
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
	LoadUserWithUsernamePort,
	RegisterUserStatePort,
	RegisterProfileStatePort,
	UpdateUserStatePort,
	UpdateProfileStatePort,
	LoadProfileWithUsernamePort,
	LoadProfileWithUserIdPort,
	LoadProfileWithProfileIdPort {

	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final UserMapper userMapper;
	private final ArticleRepository articleRepository;

	@Override
	public User loadUserWithEmail(String email) {
		UserJpaEntity userJpaEntity = userRepository.findUserJpaEntityByEmail(email)
			.orElseThrow(UserNotFoundException::new);

		return userMapper.mapToUserEntity(userJpaEntity);
	}

	@Override
	public User loadUserWithId(User.UserId userId) {
		UserJpaEntity userJpaEntity = userRepository.findById(userId.getValue())
			.orElseThrow(UserNotFoundException::new);

		return userMapper.mapToUserEntity(userJpaEntity);
	}

	@Override
	public User loadUserWithUsername(String username) {
		UserJpaEntity userJpaEntity = userRepository.findUserJpaEntityByUsername(username)
			.orElseThrow(UserNotFoundException::new);

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
			.orElseThrow(UserNotFoundException::new);

		userJpaEntity.update(user);
	}

	@Override
	public void updateProfile(Profile profile) {
		ProfileJpaEntity profileJpaEntity = profileRepository.findById(profile.getId().getValue())
			.orElseThrow(ProfileNotFoundException::new);

		profileJpaEntity.update(profile);
	}

	@Override
	public Profile loadProfileWithUserId(User.UserId userId) {
		ProfileJpaEntity profileJpaEntity = profileRepository.getProfileJpaEntityByUser(userId.getValue())
			.orElseThrow(ProfileNotFoundException::new);

		return userMapper.mapToProfileEntity(profileJpaEntity);
	}

	@Override
	public Profile loadProfileWithUsername(String username) {
		ProfileJpaEntity profileJpaEntity = profileRepository.getProfileJpaEntityByUsername(username)
			.orElseThrow(ProfileNotFoundException::new);

		return userMapper.mapToProfileEntity(profileJpaEntity);
	}

	@Override
	public Profile loadProfileWithProfileId(ProfileId profileId) {
		ProfileJpaEntity profileJpaEntity = profileRepository.findById(profileId.getValue())
			.orElseThrow(ProfileNotFoundException::new);

		return userMapper.mapToProfileEntity(profileJpaEntity);
	}

}

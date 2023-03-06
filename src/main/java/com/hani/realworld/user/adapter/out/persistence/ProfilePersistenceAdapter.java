package com.hani.realworld.user.adapter.out.persistence;

import com.hani.realworld.common.annotation.PersistenceAdapter;
import com.hani.realworld.user.application.port.out.LoadUserWithEmailPort;
import com.hani.realworld.user.application.port.out.LoadUserWithIdPort;
import com.hani.realworld.user.application.port.out.RegisterUserStatePort;
import com.hani.realworld.user.application.port.out.UpdateUserStatePort;
import com.hani.realworld.user.domain.User;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProfilePersistenceAdapter implements
	LoadUserWithEmailPort,
	LoadUserWithIdPort,
	RegisterUserStatePort,
	UpdateUserStatePort {

	@Override
	public User loadUser(String email) {
		return null;
	}

	@Override
	public User loadUser(User.UserId userId) {
		return null;
	}

	@Override
	public void registerUser(User user) {

	}

	@Override
	public void updateUserState(User user) {

	}
}

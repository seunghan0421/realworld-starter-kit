package com.hani.realworld.profile.application.port.out;

import com.hani.realworld.profile.domain.User;

public interface LoadUserWithEmailPort {
	User loadUser(String email);
}

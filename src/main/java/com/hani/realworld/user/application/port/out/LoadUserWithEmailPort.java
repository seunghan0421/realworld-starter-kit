package com.hani.realworld.user.application.port.out;

import com.hani.realworld.user.domain.User;

public interface LoadUserWithEmailPort {
	User loadUser(String email);
}

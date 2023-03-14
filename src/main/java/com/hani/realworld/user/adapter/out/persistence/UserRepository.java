package com.hani.realworld.user.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserJpaEntity, Long> {

	@Query("select u from UserJpaEntity u "
		+ "where u.email = :email")
	Optional<UserJpaEntity> findUserJpaEntityByEmail(String email);
}

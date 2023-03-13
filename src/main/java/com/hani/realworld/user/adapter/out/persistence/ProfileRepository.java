package com.hani.realworld.user.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<ProfileJpaEntity, Long> {

	@Query("select distinct p from ProfileJpaEntity p "
		+ "join fetch p.user "
		+ "where p.user.id = :userId")
	Optional<ProfileJpaEntity> getProfileJpaEntityByUser(Long userId);

	@Query("select distinct p from ProfileJpaEntity p "
		+ "join fetch p.user "
		+ "where p.user.username = :username")
	Optional<ProfileJpaEntity> getProfileJpaEntityByUsername(String username);
}

package com.hani.realworld.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserJpaEntity, Long> {
}

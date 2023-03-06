package com.hani.realworld.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileJpaEntity, Long> {
}

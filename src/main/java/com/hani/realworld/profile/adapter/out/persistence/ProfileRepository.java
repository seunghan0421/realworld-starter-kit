package com.hani.realworld.profile.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileJpaEntity, Long> {
}

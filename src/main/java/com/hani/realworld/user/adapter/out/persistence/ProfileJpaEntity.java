package com.hani.realworld.user.adapter.out.persistence;

import static com.hani.realworld.user.domain.User.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.hani.realworld.user.domain.Profile;
import com.hani.realworld.user.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "profile")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ProfileJpaEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "profile_id")
	private Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private UserJpaEntity user;

	@ElementCollection(targetClass = Long.class)
	private Set<Long> followees;

	public void update(Profile profile) {

		this.user.update(
			profile.getUser().getUsername(),
			profile.getUser().getEmail(),
			profile.getUser().getPassword().getValue(),
			profile.getUser().getBio(),
			profile.getUser().getImage());

		this.followees = profile.getFollowees().getFollowees().stream()
			.map(UserId::getValue)
			.collect(Collectors.toSet());
	}
}

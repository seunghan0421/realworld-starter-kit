package com.hani.realworld.user.adapter.out.persistence;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.Set;

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
	private Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn
	private UserJpaEntity user;

	@ElementCollection(fetch = LAZY)
	private Set<Long> followees;
}

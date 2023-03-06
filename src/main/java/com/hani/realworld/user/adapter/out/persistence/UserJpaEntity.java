package com.hani.realworld.user.adapter.out.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "profile")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJpaEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String username;

	@Column
	private String email;

	@Column
	private String password;

	@Column
	private String bio;

	@Column
	private String image;
}

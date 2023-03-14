package com.hani.realworld.user.adapter.out.persistence;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "my_user")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJpaEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id")
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

	public void update(
		String username,
		String email,
		String password,
		String bio,
		String image) {

		this.username = username;
		this.email = email;
		this.password = password;
		this.bio = bio;
		this.image = image;
	}
}

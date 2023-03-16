package com.hani.realworld.article.adapter.in.dto;

import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagResponse {

	private Set<String> tags;

	public static TagResponse of(Set<String> tags) {
		return new TagResponse(tags);
	}

}

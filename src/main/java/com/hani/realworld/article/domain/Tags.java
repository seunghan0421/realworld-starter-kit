package com.hani.realworld.article.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@EqualsAndHashCode
public class Tags {

	/**
	 * tags of Article
	 */
	private Set<String> tagSet;

	public Tags() {
		this.tagSet = new HashSet<>();
	}

	public Tags(@NonNull List<String> tagSet) {
		this.tagSet = new HashSet<>(tagSet);
	}

	public Tags(@NonNull Set<String> tagSet) {
		this.tagSet = new HashSet<>(tagSet);
	}

	public Tags(@NonNull String... tagSet) {
		this.tagSet = new HashSet<>(Arrays.asList(tagSet));
	}

	public Set<String> getTagSet() {
		return Collections.unmodifiableSet(this.tagSet);
	}

}

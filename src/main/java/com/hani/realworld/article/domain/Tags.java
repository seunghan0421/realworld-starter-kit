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
	private Set<String> tags;

	public Tags() {
		this.tags = new HashSet<>();
	}

	public Tags(@NonNull List<String> tags) {
		this.tags = new HashSet<>(tags);
	}

	public Tags(@NonNull Set<String> tags) {
		this.tags = new HashSet<>(tags);
	}

	public Tags(@NonNull String... tags) {
		this.tags = new HashSet<>(Arrays.asList(tags));
	}

	public Set<String> getTags() {
		return Collections.unmodifiableSet(this.tags);
	}

}

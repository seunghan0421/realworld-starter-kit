package com.hani.realworld.article.domain;

import static com.hani.realworld.common.util.PreConditions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.NonNull;

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

	public Tags(@NonNull String... tags) {
		this.tags = new HashSet<>(Arrays.asList(tags));
	}

	public Set<String> getTags() {
		return Collections.unmodifiableSet(this.tags);
	}

	/**
	 * The method to add Tag to Article.
	 * Put the tag(String) in the tag set.
	 *
	 * @throws IllegalStateException if tag already added to Article
	 */
	public void addTag(String tag) {
		checkState(!alreadyHaveTag(tag), "이미 추가된 태그입니다.");

		this.tags.add(tag);
	}

	/**
	 * The method to remove tag from tag set.
	 * Remove the tag in the tag set.
	 *
	 * @throws IllegalStateException if tag is not present
	 */
	public void removeTag(String tag) {
		checkState(alreadyHaveTag(tag), "존재하지 않은 태그입니다.");

		this.tags.remove(tag);
	}

	/**
	 * Tag existence check method
	 *
	 * @return boolean value whether tag set contains tag(String)
	 */
	public boolean alreadyHaveTag(String tag) {
		return this.tags.parallelStream()
			.anyMatch(t -> t.equals(tag));
	}
}

package com.hani.realworld.article.adapter.in;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.article.adapter.in.dto.response.TagResponse;
import com.hani.realworld.article.application.port.in.GetAllTagsUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/tags")
@RestController
public class TagController {

	private final GetAllTagsUseCase getAllTagsUseCase;

	@GetMapping
	ResponseEntity<TagResponse> getAllTags() {
		Set<String> tags = getAllTagsUseCase.getAllTags();

		return ResponseEntity.ok(TagResponse.of(tags));
	}
}

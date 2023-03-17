package com.hani.realworld.comment.adapter.in.dto;

import static com.hani.realworld.comment.adapter.in.dto.CommentResponse.*;

import java.util.List;
import java.util.stream.Collectors;

import com.hani.realworld.comment.application.port.in.result.CommentResult;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MultipleCommentResponse {

	private List<CommentInfo> comments;

	public static MultipleCommentResponse of(List<CommentResult> commentResults) {
		return new MultipleCommentResponse(commentResults.stream()
			.map(CommentInfo::of)
			.collect(Collectors.toList()));
	}

}

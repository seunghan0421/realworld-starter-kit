package com.hani.realworld.comment.adapter.in.dto.response;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;
import static com.hani.realworld.user.adapter.in.web.dto.response.ProfileResponse.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hani.realworld.comment.application.port.in.result.CommentResult;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {

	private CommentInfo comment;

	public static CommentResponse of(CommentResult result){
		return new CommentResponse(CommentInfo.of(result));
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class CommentInfo {
		private Long id;
		@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
		private LocalDateTime createdAt;
		@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
		private LocalDateTime updatedAt;
		private String body;
		private ProfileInfo author;

		public static CommentInfo of(CommentResult result) {
			return new CommentInfo(
				result.getId(),
				result.getCreatedAt(),
				result.getUpdatedAt(),
				result.getBody(),
				ProfileInfo.of(result.getAuthor()));
		}
	}

}

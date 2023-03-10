package com.hani.realworld.common.restdocs;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.common.exception.ErrorCode;
import com.hani.realworld.common.exception.ErrorResponse;

@RestController
@RequestMapping("/test")
public class CommonDocController {

	@PostMapping("/error")
	public ResponseEntity<ErrorResponse> errorSample(@RequestBody @Valid SampleRequest dto) {
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	public static class SampleRequest {
		private String name;
		private String email;

		private SampleRequest() {}

		public SampleRequest(String name, String email) {
			this.name = name;
			this.email = email;
		}
	}

}

package com.hani.realworld.common.restdocs;

import static com.hani.realworld.common.restdocs.CommonDocController.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import com.hani.realworld.ControllerTest;

@WebMvcTest(CommonDocController.class)
public class CommonDocControllerTest extends ControllerTest {

	@Test
	void errorSample() throws Exception {
		SampleRequest sampleRequest = new SampleRequest("name", "hhh.baver");

		mockMvc.perform(
				RestDocumentationRequestBuilders.post("/test/error")
					.contentType(MediaType.APPLICATION_JSON)
					.content(createJson(sampleRequest))
			)
			.andExpect(status().isBadRequest())
			.andDo(
				restDocs.document(
					responseFields(
						fieldWithPath("message").description("에러 메시지"),
						fieldWithPath("code").description("Error Code"),
						fieldWithPath("errors").description("Error 값 배열 값"),
						fieldWithPath("status").description("Error 값 배열 값")
						// fieldWithPath("errors[0].field").description("문제 있는 필드"),
						// fieldWithPath("errors[0].value").description("문제가 있는 값"),
						// fieldWithPath("errors[0].reason").description("문재가 있는 이유")
					)
				)
			);
	}
}

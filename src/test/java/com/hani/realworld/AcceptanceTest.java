package com.hani.realworld;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.infra.jwt.JwtAuthenticationFilter.*;
import static com.hani.realworld.infra.jwt.JwtProvider.*;
import static io.restassured.RestAssured.*;
import static org.springframework.http.MediaType.*;

import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hani.realworld.article.adapter.in.dto.request.CreateArticleRequest;
import com.hani.realworld.article.adapter.in.dto.response.ArticleResponse;
import com.hani.realworld.article.domain.Article;
import com.hani.realworld.user.adapter.in.web.dto.request.LoginUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.response.RegisterUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.response.UserResponse;
import com.hani.realworld.user.domain.User;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

	protected static final String AUTHORIZATION_HEADER_NAME = AUTHORIZATION_HEADER;

	@Autowired
	protected ObjectMapper objectMapper;

	protected String token;

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() throws JsonProcessingException {
		RestAssured.port = port;
		token = registerAndLogin(USER1, "password");
	}

	public void register(User user, String password) throws JsonProcessingException {
		RegisterUserRequest request = new RegisterUserRequest(
			user.getUsername(),
			user.getEmail(),
			password);
		String body = objectMapper.writeValueAsString(request);
		post("/api/users", body, HttpStatus.SC_CREATED, UserResponse.class);
	}

	private String login(User user, String password) throws JsonProcessingException {
		LoginUserRequest request = new LoginUserRequest(
			user.getEmail(),
			password
		);
		String body = objectMapper.writeValueAsString(request);
		UserResponse response = post("/api/users/login", body, HttpStatus.SC_OK,
			UserResponse.class);
		return response.getToken();
	}

	public String registerAndLogin(User user, String password) throws JsonProcessingException {
		register(user, password);
		return login(user, password);
	}

	public void createArticle(Article article) throws JsonProcessingException {
		CreateArticleRequest request = new CreateArticleRequest(
			article.getTitle(),
			article.getBody(),
			article.getBody(),
			new ArrayList<>(article.getTags().getTags()));
		String body = objectMapper.writeValueAsString(request);
		post("/api/articles", body, token, HttpStatus.SC_CREATED, ArticleResponse.class);
	}

	protected <T> T post(String uri, String body, int status, Class<T> cls) {
		return
			given()
				.log().all()
				.accept(APPLICATION_JSON_VALUE)
				.contentType(APPLICATION_JSON_VALUE)
				.body(body)
				.when()
				.post(uri)
				.then()
				.log().all()
				.statusCode(status)
				.contentType(APPLICATION_JSON_VALUE)
				.extract().as(cls);
	}

	protected <T> T post(String uri, String body, String token, int status, Class<T> cls) {
		return
			given()
				.log().all()
				.accept(APPLICATION_JSON_VALUE)
				.contentType(APPLICATION_JSON_VALUE)
				.header(AUTHORIZATION_HEADER_NAME, HEADER_PREFIX + token)
				.body(body)
				.when()
				.post(uri)
				.then()
				.log().all()
				.statusCode(status)
				.contentType(APPLICATION_JSON_VALUE)
				.extract().as(cls);
	}

	protected <T> T get(String uri, int status, Class<T> cls) {
		return
			given()
				.log().all()
				.when()
				.get(uri)
				.then()
				.log().all()
				.statusCode(status)
				.contentType(APPLICATION_JSON_VALUE)
				.extract().as(cls);
	}

	protected <T> T get(String uri, String token, int status, Class<T> cls) {
		return
			given()
				.log().all()
				.header(AUTHORIZATION_HEADER_NAME, HEADER_PREFIX + token)
				.when()
				.get(uri)
				.then()
				.log().all()
				.statusCode(status)
				.contentType(APPLICATION_JSON_VALUE)
				.extract().as(cls);
	}

	protected <T> T put(String uri, String body, String token, int status, Class<T> cls) {
		return
			given()
				.log().all()
				.accept(APPLICATION_JSON_VALUE)
				.contentType(APPLICATION_JSON_VALUE)
				.header(AUTHORIZATION_HEADER_NAME, HEADER_PREFIX + token)
				.body(body)
				.when()
				.put(uri)
				.then()
				.log().all()
				.statusCode(status)
				.contentType(APPLICATION_JSON_VALUE)
				.extract().as(cls);
	}

	protected void delete(String uri, String token) {
		given()
			.log().all()
			.header(AUTHORIZATION_HEADER_NAME, HEADER_PREFIX + token)
			.when()
			.delete(uri)
			.then()
			.log().all()
			.statusCode(HttpStatus.SC_NO_CONTENT);
	}

	protected <T> T delete(String uri, String token, int status, Class<T> cls) {
		return
			given()
				.log().all()
				.header(AUTHORIZATION_HEADER_NAME, HEADER_PREFIX + token)
				.when()
				.delete(uri)
				.then()
				.log().all()
				.statusCode(status)
				.contentType(APPLICATION_JSON_VALUE)
				.extract().as(cls);
	}
}

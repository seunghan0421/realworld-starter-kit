package com.hani.realworld.infra.jwt;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hani.realworld.user.application.port.out.LoadUserWithEmailPort;
import com.hani.realworld.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OptionalUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private final LoadUserWithEmailPort loadUserWithEmailPort;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(OptionalUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String token = (String)authentication.getCredentials();
		String email = (String)authentication.getPrincipal();

		if (StringUtils.isEmpty(token)) {
			return null;
		}

		User user = loadUserWithEmailPort.loadUserWithEmail(email);

		return new LoginToken(user.getId().getValue(), token, user.getEmail());
	}
}

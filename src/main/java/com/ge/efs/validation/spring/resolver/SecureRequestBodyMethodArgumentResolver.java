package com.ge.efs.validation.spring.resolver;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

public class SecureRequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private static SecureRequestBodyMethodArgumentResolver instance;

	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor = null;
	private Validator validator;

	private SecureRequestBodyMethodArgumentResolver(
			RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
		super();
		this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
	}

	public static SecureRequestBodyMethodArgumentResolver getInstance(
			RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
		if (instance == null)
			instance = new SecureRequestBodyMethodArgumentResolver(requestMappingHandlerAdapter);
		return instance;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer modelViewContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		Object value = getRequestResponseBodyMethodProcessor().resolveArgument(parameter,
				modelViewContainer, webRequest, binderFactory);
		if (parameter.hasParameterAnnotation(Valid.class)) {
			validateObject(value);
		}
		return value;
	}

	private void validateObject(Object value) {
		Set<ConstraintViolation<Object>> violations = validator.validate(value);
    if (!violations.isEmpty()) {
        throw new ValidationException();
    }
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return getRequestResponseBodyMethodProcessor().supportsParameter(parameter);
	}

	private RequestResponseBodyMethodProcessor getRequestResponseBodyMethodProcessor() {

		if (requestResponseBodyMethodProcessor == null) {
			List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter
					.getMessageConverters();
			requestResponseBodyMethodProcessor = new RequestResponseBodyMethodProcessor(messageConverters);
		}
		return requestResponseBodyMethodProcessor;
	}

}

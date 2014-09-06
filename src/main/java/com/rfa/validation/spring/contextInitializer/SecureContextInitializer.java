package com.rfa.validation.spring.contextInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.AuthenticationException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.rfa.validation.exception.InitializationException;
import com.rfa.validation.spring.resolver.SecureRequestBodyMethodArgumentResolver;

/**
 * This class serves to initialize the configuration of this API and, to extend
 * the default functionality of Spring, by integrating it with ESAPI.
 * 
 * @author rafael.benitez
 * 
 */
public class SecureContextInitializer {

	/**
	 * Call this function after initializing the Spring context to add new
	 * validation features to the default behavior of Spring.
	 * 
	 * @param servletContext
	 * @throws InitializationException
	 */
	public static void intializeSecureContext(ServletContext servletContext)
			throws InitializationException {
		ApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		setSecureHandlerAdappter(applicationContext);
		try {
			ESAPI.authenticator().createUser("rafa", "pwd", "pwd");
		} catch (AuthenticationException e) {
			throw new InitializationException("Error when creating the user", e);
		}
	}

	private static void setSecureHandlerAdappter(
			ApplicationContext applicationContext) throws InitializationException {
		Map<String, RequestMappingHandlerAdapter> handlerAdapters = applicationContext
				.getBeansOfType(RequestMappingHandlerAdapter.class);
		for (String name : handlerAdapters.keySet()) {
			RequestMappingHandlerAdapter handlerAdapter = handlerAdapters.get(name);
			addSecureCustomResolver(handlerAdapter);
		}
	}

	private static void addSecureCustomResolver(
			RequestMappingHandlerAdapter handlerAdapter)
			throws InitializationException {
		SecureRequestBodyMethodArgumentResolver resolver = new SecureRequestBodyMethodArgumentResolver(
				handlerAdapter);
		List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>(
				handlerAdapter.getArgumentResolvers());
		List<HandlerMethodArgumentResolver> customResolvers = handlerAdapter
				.getCustomArgumentResolvers();
		if (customResolvers == null) {
			customResolvers = new ArrayList<>();
		}
		customResolvers.add(resolver);
		argumentResolvers.removeAll(customResolvers);
		argumentResolvers.addAll(0, customResolvers);
		handlerAdapter.setArgumentResolvers(argumentResolvers);
		handlerAdapter.setCustomArgumentResolvers(customResolvers);
	}
}

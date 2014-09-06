package com.rfa.validation.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.rfa.validation.exception.InitializationException;
import com.rfa.validation.spring.contextInitializer.SecureContextInitializer;

public class SecurityFilter implements Filter {

	 private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		chain.doFilter(httpRequest, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext servletContext = filterConfig.getServletContext();
		try {
			SecureContextInitializer.intializeSecureContext(servletContext);
		} catch (InitializationException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void destroy() {

	}
}

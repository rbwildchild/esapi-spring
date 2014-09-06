package com.rfa.validation.spring.resolver;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.owasp.esapi.ESAPI;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

public class SecureRequestBodyMethodArgumentResolver implements
		HandlerMethodArgumentResolver {

	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor = null;

	// private Validator validator;

	public SecureRequestBodyMethodArgumentResolver(
			RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
		super();
		this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
		// ValidatorFactory validatorFactory = Validation
		// .buildDefaultValidatorFactory();
		// validator = validatorFactory.getValidator();
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer modelViewContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		Object object = getRequestResponseBodyMethodProcessor().resolveArgument(
				parameter, modelViewContainer, webRequest, binderFactory);
		// if (parameter.hasParameterAnnotation(Valid.class)) {
		validateObject(object);
		// }
		return object;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return getRequestResponseBodyMethodProcessor().supportsParameter(parameter);
	}

	private RequestResponseBodyMethodProcessor getRequestResponseBodyMethodProcessor() {

		if (requestResponseBodyMethodProcessor == null) {
			List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter
					.getMessageConverters();
			requestResponseBodyMethodProcessor = new RequestResponseBodyMethodProcessor(
					messageConverters);
		}
		return requestResponseBodyMethodProcessor;
	}

	private void validateObject(Object object) {
		// Set<ConstraintViolation<Object>> violations = validator.validate(value);
		// if (!violations.isEmpty()) {
		// throw new ValidationException();
		// }
		if (object instanceof String) {
			ESAPI.validator().isValidInput(object.getClass() + ":" + object,
					(String) object, "SafeString", 60, true);
		} else {
			try {
				PropertyDescriptor[] descriptors = PropertyUtils
						.getPropertyDescriptors(object);
				for (PropertyDescriptor descriptor : descriptors) {
					if (descriptor.getPropertyType() != Class.class) {
						Object value = descriptor.getReadMethod().invoke(object,
								(Object[]) null);
						if (value != null && value instanceof String)
							System.out.println(descriptor.getDisplayName()+" "+ESAPI.validator().isValidInput(descriptor.getDisplayName() + ":" + value,
									(String) value, "SafeString", 60, true));
					}
				}
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}

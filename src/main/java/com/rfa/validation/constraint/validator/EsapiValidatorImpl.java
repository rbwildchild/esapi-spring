package com.rfa.validation.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.owasp.esapi.ESAPI;

import com.rfa.validation.constraint.EsapiValidator;

public class EsapiValidatorImpl implements ConstraintValidator<EsapiValidator, String> {

	private String type;
	private int maxLength;
	private boolean allowNull;
	
	@Override
	public void initialize(EsapiValidator esapiValidator) {
		this.type = esapiValidator.type();
		this.maxLength = esapiValidator.maxLength();
		this.allowNull = esapiValidator.allowNull();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext validatorContext) {
		return ESAPI.validator().isValidInput(value + " " + type, value, type, maxLength, allowNull);
	}

}

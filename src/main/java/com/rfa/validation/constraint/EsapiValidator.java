package com.rfa.validation.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rfa.validation.constraint.validator.EsapiValidatorImpl;

@Documented
@Constraint(validatedBy = EsapiValidatorImpl.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface EsapiValidator {
    String message() default "{com.rfa.validation.constraint.EsapiValidator.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String type();
    int maxLength() default Integer.MAX_VALUE;
    boolean allowNull() default true;
}

package com.file.sorting.infrastructure.validation;

import com.file.sorting.infrastructure.validation.impl.FormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FormatValidator.class)
@Target({ElementType.PARAMETER,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Format {
    String message() default "Illegal file format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}

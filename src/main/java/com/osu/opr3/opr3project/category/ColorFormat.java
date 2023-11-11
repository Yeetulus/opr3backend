package com.osu.opr3.opr3project.category;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ColorFormatValidator.class)
public @interface ColorFormat {
    String message() default "Invalid color format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean checkLightness() default false;
}
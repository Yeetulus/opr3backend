package com.osu.opr3.opr3project.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaskDatesFormatValidator.class)
@Documented
public @interface TaskDatesFormat {

    String message() default "Invalid date range or subtask date range.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

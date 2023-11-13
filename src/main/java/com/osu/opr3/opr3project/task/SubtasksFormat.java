package com.osu.opr3.opr3project.task;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SubtasksFormatValidator.class)
@Documented
public @interface SubtasksFormat {
    String message() default "Either subtasks or parentTask must be not null, but not both";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
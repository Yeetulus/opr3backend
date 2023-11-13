package com.osu.opr3.opr3project.task;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SubtasksFormatValidator implements ConstraintValidator<SubtasksFormat, Task> {
    @Override
    public void initialize(SubtasksFormat constraintAnnotation) {
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext context) {
        return (task.getSubtasks() == null && task.getParentTask() == null) ||
                (task.getSubtasks() != null && task.getParentTask() == null) ||
                (task.getSubtasks() == null && task.getParentTask() != null);
    }
}
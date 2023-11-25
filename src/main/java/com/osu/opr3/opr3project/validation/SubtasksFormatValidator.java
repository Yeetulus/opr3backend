package com.osu.opr3.opr3project.validation;

import com.osu.opr3.opr3project.task.Task;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SubtasksFormatValidator implements ConstraintValidator<SubtasksFormat, Task> {
    @Override
    public void initialize(SubtasksFormat constraintAnnotation) {
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext context) {
        boolean parentNull = task.getParentTask() == null;
        boolean subtasksNull = task.getSubtasks() == null || task.getSubtasks().isEmpty();
        return
                (parentNull && !subtasksNull) ||
                (!parentNull && subtasksNull) ||
                (parentNull && subtasksNull);

    }
}
package com.osu.opr3.opr3project.validation;

import com.osu.opr3.opr3project.task.TaskRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.util.List;

public class TaskDatesFormatValidator implements ConstraintValidator<TaskDatesFormat, TaskRequest> {

    @Override
    public void initialize(TaskDatesFormat constraintAnnotation) {
    }

    @Override
    public boolean isValid(TaskRequest taskRequest, ConstraintValidatorContext context) {
        if (taskRequest == null) {
            return true;
        }

        LocalDateTime fromDate = taskRequest.getFromDate();
        LocalDateTime toDate = taskRequest.getToDate();

        boolean valid = fromDate == null && toDate == null ||
                fromDate != null && toDate == null ||

                (fromDate != null && toDate != null &&
                !toDate.isBefore(fromDate) && fromDate.toLocalDate().isEqual(toDate.toLocalDate()));

        return valid && validateSubtasks(taskRequest.getSubtasks());
    }

    private boolean validateSubtasks(List<TaskRequest> subtasks) {
        if (subtasks == null) {
            return true;
        }
        for (TaskRequest subtask : subtasks) {
            if (!isValid(subtask, null)) {
                return false;
            }
        }

        return true;
    }
}
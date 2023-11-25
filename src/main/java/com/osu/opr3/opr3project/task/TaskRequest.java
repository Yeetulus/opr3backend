package com.osu.opr3.opr3project.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.osu.opr3.opr3project.validation.TaskDatesFormat;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TaskDatesFormat
public class TaskRequest {

    @NotBlank
    private String name;
    private String description;

    private Long id;
    private Long categoryId;
    private boolean completed = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime toDate;

    @Nullable
    private List<TaskRequest> subtasks;
}

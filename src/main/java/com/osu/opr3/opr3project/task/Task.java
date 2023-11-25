package com.osu.opr3.opr3project.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.osu.opr3.opr3project.category.Category;
import com.osu.opr3.opr3project.validation.SubtasksFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SubtasksFormat
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime toDate;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    private boolean completed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "parentTask")
    private List<Task> subtasks;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    public static class TaskBuilder {

        public TaskBuilder subtasks(List<TaskRequest> subtaskRequests) {
            if (subtaskRequests != null && !subtaskRequests.isEmpty()) {
                this.taskType = TaskType.COMPLEX;
                this.subtasks = subtaskRequests.stream()
                    .map(subtaskRequest -> Task.builder()
                        .name(subtaskRequest.getName())
                        .description(subtaskRequest.getDescription())
                        .category(category)
                        .taskType(TaskType.SIMPLE)
                        .build())
                    .collect(Collectors.toList());
            } else {
                this.taskType = TaskType.SIMPLE;
            }
            return this;
        }

    }

}

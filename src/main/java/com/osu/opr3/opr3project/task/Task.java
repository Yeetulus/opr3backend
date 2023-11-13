package com.osu.opr3.opr3project.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.osu.opr3.opr3project.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        private final List<Task> subtasks = new ArrayList<>();
        public TaskBuilder fromDateAndToDate(LocalDateTime fromDate, LocalDateTime toDate) {
            if (fromDate != null && toDate != null) {
                this.taskType = TaskType.COMPLEX;
            }
            return this;
        }
        public TaskBuilder subtasks(List<TaskRequest> subtaskRequests) {
            if (subtaskRequests != null && !subtaskRequests.isEmpty()) {
                this.taskType = TaskType.COMPLEX;
                List<Task> subtasks = new ArrayList<>();
                for (TaskRequest subtaskRequest : subtaskRequests) {
                    Task subtask = Task.builder()
                            .name(subtaskRequest.getName())
                            .description(subtaskRequest.getDescription())
                            .category(this.category)
                            .build();
                    subtasks.add(subtask);
                }
                this.subtasks.addAll(subtasks);
            } else {
                this.taskType = TaskType.SIMPLE;
            }
            return this;
        }

    }
}

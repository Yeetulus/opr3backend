package com.osu.opr3.opr3project.task;

import com.osu.opr3.opr3project.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    @Override
    public Task createTask(TaskRequest request) {
        var newTask = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(categoryService.getCategory(request.getCategoryId()))
                .fromDateAndToDate(request.getFromDate(), request.getToDate())
                .subtasks(request.getSubtasks())
                .build();

        return taskRepository.save(newTask);
    }
}

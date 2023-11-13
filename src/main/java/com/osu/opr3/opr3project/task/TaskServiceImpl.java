package com.osu.opr3.opr3project.task;

import com.osu.opr3.opr3project.category.CategoryService;
import com.osu.opr3.opr3project.category.CategoryServiceImpl;
import com.osu.opr3.opr3project.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
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

    @Override
    public Task updateTask(TaskRequest request, Long id) {
        var existingTask = taskRepository.findById(id).orElseThrow(
                () -> {
                    final String warning = String.format("Task of ID %s not found", id);
                    logger.warn(warning);
                    return new ItemNotFoundException(warning);
                });

        existingTask.setName(request.getName());
        existingTask.setDescription(request.getDescription());
        existingTask.setCompleted(request.isCompleted());
        if(existingTask.getParentTask() == null){
            existingTask.setFromDate(request.getFromDate());
            existingTask.setToDate(request.getToDate());
        }

        return taskRepository.save(existingTask);
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> {
                    final String warning = String.format("Task of ID %s not found", id);
                    logger.warn(warning);
                    return new ItemNotFoundException(warning);
                });
    }

    @Override
    public Boolean deleteTask(Long id) {
        taskRepository.deleteById(id);
        return true;
    }
}

package com.osu.opr3.opr3project.task;

import com.osu.opr3.opr3project.category.Category;
import com.osu.opr3.opr3project.category.CategoryService;
import com.osu.opr3.opr3project.exception.ItemNotFoundException;
import com.osu.opr3.opr3project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    @Override
    public Task createTask(TaskRequest request) {
        var category = categoryService.getCategory(request.getCategoryId());
        var newTask = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(category)
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .subtasks(request.getSubtasks()).build();
        if(newTask.getSubtasks() != null) newTask.getSubtasks().forEach(subtask -> subtask.setParentTask(newTask));

        return taskRepository.save(newTask);
    }

    @Override
    public Task updateTask(TaskRequest request) {
        var existingTask = taskRepository.findById(request.getId()).orElseThrow(
                () -> new ItemNotFoundException(request.getId()));

        existingTask.setName(request.getName());
        existingTask.setDescription(request.getDescription());
        existingTask.setCompleted(request.isCompleted());
        existingTask.setFromDate(request.getFromDate());
        existingTask.setToDate(request.getToDate());

        if(request.getSubtasks() != null && !request.getSubtasks().isEmpty()){
            request.getSubtasks().forEach(subtaskRequest ->{
                Optional<Task> optionalSubtask = existingTask.getSubtasks().stream()
                        .filter(subtask -> Objects.equals(subtask.getId(), subtaskRequest.getId()))
                        .findFirst();

                if(optionalSubtask.isPresent()){
                    var subtask = optionalSubtask.get();

                    subtask.setName(subtaskRequest.getName());
                    subtask.setDescription(subtaskRequest.getDescription());
                    subtask.setCompleted(subtaskRequest.isCompleted());
                    subtask.setFromDate(subtaskRequest.getFromDate());
                    subtask.setToDate(subtaskRequest.getToDate());
                }
            });
        }

        return taskRepository.save(existingTask);
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(id));
    }

    @Override
    public List<Task> getTasks(User user) {
        List<Long> userCategoryIds = user.getCategories()
                .stream()
                .map(Category::getId)
                .toList();

        return taskRepository.findTasksByUserAndCategoryIds(userCategoryIds);
    }

    @Override
    public List<Task> getTasks(List<Long> categories) {
        return taskRepository.findTasksByUserAndCategoryIds(categories);
    }

    @Override
    public Boolean deleteTask(Long id) {
        taskRepository.deleteById(id);
        return true;
    }
}

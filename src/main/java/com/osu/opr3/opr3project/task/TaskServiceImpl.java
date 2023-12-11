package com.osu.opr3.opr3project.task;

import com.osu.opr3.opr3project.category.Category;
import com.osu.opr3.opr3project.category.CategoryService;
import com.osu.opr3.opr3project.exception.ItemNotFoundException;
import com.osu.opr3.opr3project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                .build();

        newTask = taskRepository.save(newTask);

        List<Task> subtasks = new ArrayList<>();
        if (request.getSubtasks() != null && !request.getSubtasks().isEmpty()) {
            for (int i = 0; i < request.getSubtasks().size(); i++) {
                subtasks.add(Task.builder()
                        .name(request.getSubtasks().get(i).getName())
                        .description(request.getSubtasks().get(i).getDescription())
                        .parentTask(newTask)
                        .build());
            }
            subtasks = taskRepository.saveAll(subtasks);
        }
        newTask.setSubtasks(subtasks);
        return taskRepository.save(newTask);
    }

    @Override
    public Task updateTask(TaskRequest request) {
        var existingTask = taskRepository.findById(request.getId()).orElseThrow(
                () -> new ItemNotFoundException(request.getId()));

        var newCategory = request.getNewCategoryId()!= null? categoryService.getCategory(request.getNewCategoryId()):
                categoryService.getCategory(request.getCategoryId());

        existingTask.setName(request.getName());
        existingTask.setDescription(request.getDescription());
        existingTask.setFromDate(request.getFromDate());
        existingTask.setToDate(request.getToDate());
        existingTask.setCategory(newCategory);

        Boolean alterSubtasks = request.getAlterSubtasks();
        if(alterSubtasks != null && alterSubtasks){
            existingTask.getSubtasks().forEach(subtask -> subtask.setParentTask(null));
            taskRepository.deleteAll(existingTask.getSubtasks());
            existingTask.setSubtasks(new ArrayList<>());

            if(request.getSubtasks()!=null) {
                request.getSubtasks().forEach(s -> {
                    var newSubtask = Task.builder()
                            .name(s.getName())
                            .description(s.getDescription())
                            .parentTask(existingTask)
                            .category(newCategory)
                            .completed(s.isCompleted())
                            .build();
                    taskRepository.save(newSubtask);
                    existingTask.getSubtasks().add(newSubtask);
                });
            }
        }
        else{
            existingTask.setCompleted(request.isCompleted());
            for (int i = 0; i < existingTask.getSubtasks().size(); i++) {
                existingTask.getSubtasks().get(i).setCompleted(request.getSubtasks().get(i).isCompleted());
            }
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

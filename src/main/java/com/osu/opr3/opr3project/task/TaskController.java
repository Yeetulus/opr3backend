package com.osu.opr3.opr3project.task;

import com.osu.opr3.opr3project.category.CategoryService;
import com.osu.opr3.opr3project.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/tasks")
public class TaskController {

    private final CategoryService categoryService;
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@NonNull HttpServletRequest request,
                                           @Valid @RequestBody TaskRequest taskRequest) {
        categoryService.hasUserCategory(
                (User) request.getAttribute("jwtUser"),
                taskRequest.getCategoryId());
        return ResponseEntity.ok(taskService.createTask(taskRequest));
    }

    @PutMapping("/edit")
    public ResponseEntity<Task> editTask(@NonNull HttpServletRequest request,
                                         @Valid @RequestBody TaskRequest taskRequest,
                                         @RequestParam Long taskId) {
        categoryService.hasUserCategoryAndTask(
                (User) request.getAttribute("jwtUser"),
                taskRequest.getCategoryId(),
                taskId);
        return ResponseEntity.ok(taskService.updateTask(taskRequest, taskId));
    }

    @GetMapping("/get")
    public ResponseEntity<Task> getTask(@NonNull HttpServletRequest request,
                                        @RequestParam Long categoryId,
                                        @RequestParam Long taskId) {
        categoryService.hasUserCategoryAndTask(
                (User) request.getAttribute("jwtUser"),
                categoryId,
                taskId);
        return ResponseEntity.ok(taskService.getTask(taskId));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteTask(@NonNull HttpServletRequest request,
                                              @RequestParam Long categoryId,
                                              @RequestParam Long taskId) {
        categoryService.hasUserCategoryAndTask(
                (User) request.getAttribute("jwtUser"),
                categoryId,
                taskId);
        return ResponseEntity.ok(taskService.deleteTask(taskId));
    }

}

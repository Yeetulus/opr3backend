package com.osu.opr3.opr3project.task;

import com.osu.opr3.opr3project.category.CategoryService;
import com.osu.opr3.opr3project.user.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/tasks")
public class TaskController {

    private final CategoryService categoryService;
    private final TaskService taskService;
    @PreAuthorize("@categoryService.hasUserCategory(#id, @UserUtil.castToUser(#request.getAttribute('jwtUser')))")
    public ResponseEntity<Task> createTask(@NonNull HttpServletRequest request, @RequestParam TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.createTask(taskRequest));
    }

}

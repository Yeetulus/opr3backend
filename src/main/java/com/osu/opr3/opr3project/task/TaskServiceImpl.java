package com.osu.opr3.opr3project.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(TaskRequest request) {
        var newTask = Task.builder().build();
        return newTask;
    }
}

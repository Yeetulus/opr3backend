package com.osu.opr3.opr3project.task;

public interface TaskService {

    Task createTask(TaskRequest request);
    Task updateTask(TaskRequest request, Long id);
    Task getTask(Long id);
    Boolean deleteTask(Long id);
}

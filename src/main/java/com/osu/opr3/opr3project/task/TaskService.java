package com.osu.opr3.opr3project.task;

import com.osu.opr3.opr3project.user.User;

import java.util.List;

public interface TaskService {

    Task createTask(TaskRequest request);
    Task updateTask(TaskRequest request);
    Task getTask(Long id);
    List<Task> getTasks(User user);
    List<Task> getTasks(List<Long> categories);
    Boolean deleteTask(Long id);
}

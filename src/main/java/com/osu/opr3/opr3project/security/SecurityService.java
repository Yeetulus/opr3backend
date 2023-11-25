package com.osu.opr3.opr3project.security;

import com.osu.opr3.opr3project.exception.ItemNotFoundException;
import com.osu.opr3.opr3project.exception.ItemNotOwnedException;
import com.osu.opr3.opr3project.task.TaskRequest;
import com.osu.opr3.opr3project.user.User;

public interface SecurityService {

    void hasUserCategory(User user, Long categoryId) throws ItemNotOwnedException, ItemNotFoundException;
    void hasUserCategoryAndTask(User user, Long categoryId, Long taskId) throws ItemNotOwnedException, ItemNotFoundException;
    void hasUserTasks(User user, TaskRequest request) throws ItemNotOwnedException, ItemNotFoundException;

}

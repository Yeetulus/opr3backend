package com.osu.opr3.opr3project.security;

import com.osu.opr3.opr3project.category.CategoryService;
import com.osu.opr3.opr3project.exception.ItemNotFoundException;
import com.osu.opr3.opr3project.exception.ItemNotOwnedException;
import com.osu.opr3.opr3project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService{

    private final CategoryService categoryService;
    @Override
    public void hasUserCategory(User user, Long categoryId) throws ItemNotFoundException, ItemNotOwnedException {
        var category = categoryService.getCategory(categoryId);

        if(user.getCategories().stream()
                .anyMatch(c -> user.getId().equals(c.getUser().getId()))) return;

        throw new ItemNotOwnedException(user.getEmail(), category.getId());
    }
    @Override
    public void hasUserCategoryAndTask(User user, Long categoryId, Long taskId) throws ItemNotOwnedException, ItemNotFoundException {
        var category = categoryService.getCategory(categoryId);

        var taskNotExists = category.getTasks().stream()
                .noneMatch(t -> t.getId().equals(taskId));
        if (taskNotExists) throw new ItemNotFoundException(taskId, categoryId);

        if(user.getCategories().stream()
                .anyMatch(c -> user.getId().equals(c.getUser().getId()))) return;

        throw new ItemNotOwnedException(user.getEmail(), category.getId());
    }

}

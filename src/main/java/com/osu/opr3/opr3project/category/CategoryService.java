package com.osu.opr3.opr3project.category;

import com.osu.opr3.opr3project.exception.ItemNotFoundException;
import com.osu.opr3.opr3project.exception.ItemNotOwnedException;
import com.osu.opr3.opr3project.user.User;

import java.util.List;

public interface CategoryService {

    Category createCategory(User user, CategoryRequest request);
    Category editCategory(Long id, CategoryRequest request) throws ItemNotFoundException;
    Category getCategory(Long id) throws ItemNotFoundException;
    boolean deleteCategory(Long categoryId) throws ItemNotFoundException;
    void hasUserCategory(User user, Long categoryId) throws ItemNotOwnedException, ItemNotFoundException;
    void hasUserCategoryAndTask(User user, Long categoryId, Long taskId) throws ItemNotOwnedException, ItemNotFoundException;
    List<Category> getAllUserCategories(User user);
}

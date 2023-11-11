package com.osu.opr3.opr3project.category;

import com.osu.opr3.opr3project.exception.ItemNotFoundException;
import com.osu.opr3.opr3project.user.User;

public interface CategoryService {

    Category createCategory(CategoryRequest request);
    Category editCategory(Long id, CategoryRequest request) throws ItemNotFoundException;
    boolean hasUserCategory(User user, Long categoryId);
}

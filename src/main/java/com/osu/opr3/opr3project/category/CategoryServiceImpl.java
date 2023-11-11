package com.osu.opr3.opr3project.category;

import com.osu.opr3.opr3project.exception.ItemNotFoundException;
import com.osu.opr3.opr3project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Override
    public Category createCategory(CategoryRequest request) {
        return null;
    }
    @Override
    public Category editCategory(Long id, CategoryRequest request) throws ItemNotFoundException {
        return null;
    }

    @Override
    public boolean hasUserCategory(User user, Long categoryId) {
        return user.getCategories().stream()
                .anyMatch(category -> category.getId().equals(categoryId));
    }
}

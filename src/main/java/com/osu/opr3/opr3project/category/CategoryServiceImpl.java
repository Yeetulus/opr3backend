package com.osu.opr3.opr3project.category;

import com.osu.opr3.opr3project.exception.ItemNotFoundException;
import com.osu.opr3.opr3project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(User user, CategoryRequest request) {
        var newCategory = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .color(request.getColor())
                .tasks(new ArrayList<>())
                .user(user)
                .build();
        return categoryRepository.save(newCategory);
    }
    @Override
    public Category editCategory(Long id, CategoryRequest request) throws ItemNotFoundException {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setColor(request.getColor());
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) throws ItemNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }
    @Override
    public boolean deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return true;
    }




    @Override
    public List<Category> getAllUserCategories(User user) {
        var unfiltered = categoryRepository.findAllByUserId(user.getId());
        unfiltered.forEach(c -> c.setTasks(c.getTasks().stream()
                .filter(t -> t.getParentTask() == null)
                .collect(Collectors.toList())));

        return unfiltered;
    }
}

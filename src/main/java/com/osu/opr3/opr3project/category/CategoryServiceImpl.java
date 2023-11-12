package com.osu.opr3.opr3project.category;

import com.osu.opr3.opr3project.exception.ItemNotFoundException;
import com.osu.opr3.opr3project.exception.ItemNotOwnedException;
import com.osu.opr3.opr3project.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(User user, CategoryRequest request) {
        var newCategory = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .color(request.getColor())
                .user(user)
                .build();
        return categoryRepository.save(newCategory);
    }
    @Override
    public Category editCategory(Long id, CategoryRequest request) throws ItemNotFoundException {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    final String warning = String.format("Category with ID %s not found", id);
                    logger.warn(warning);
                    return new ItemNotFoundException(warning);});
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setColor(request.getColor());
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) throws ItemNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    final String warning = String.format("Category with ID %s not found", id);
                    logger.warn(warning);
                    return new ItemNotFoundException(warning);});
    }


    @Override
    public boolean deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return true;
    }

    @Override
    public boolean hasUserCategory(User user, Long categoryId) throws ItemNotFoundException, ItemNotOwnedException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    final String warning = String.format("Category with ID %s not found", categoryId);
                    logger.warn(warning);
                    return new ItemNotFoundException(warning);
                });

        if (!user.getCategories().contains(category)) {
            final String warning = String.format("User with ID %s does not own category %s", user.getId(), category.getId());
            logger.warn(warning);
            throw new ItemNotOwnedException(warning);
        }
        return true;
    }

    @Override
    public List<Category> getAllUserCategories(User user) {
        return categoryRepository.findByUserId(user.getId());
    }
}

package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.CategoryRequest;
import com.sundaymvp.invoice_api.dto.response.CategoryResponse;
import com.sundaymvp.invoice_api.entity.Category;
import com.sundaymvp.invoice_api.exception.ResourceNotFoundException;
import com.sundaymvp.invoice_api.mapper.CategoryMapper;
import com.sundaymvp.invoice_api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryResponse getCategoryById(Long id) {

        Objects.requireNonNull(id, "Category id must not be null");

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return CategoryMapper.toResponse(category);
    }

    public Category saveCategory(CategoryRequest request) {

        Objects.requireNonNull(request, "Category request must not be null");

        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category already exists");
        }

        Category category = CategoryMapper.toEntity(request);

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryRequest request) {

        Objects.requireNonNull(id, "Category id must not be null");
        Objects.requireNonNull(request, "Category request must not be null");

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setStatus(request.getStatus());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {

        Objects.requireNonNull(id, "Category id must not be null");

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }
}
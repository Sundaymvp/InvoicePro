package com.sundaymvp.invoice_api.mapper;

import com.sundaymvp.invoice_api.dto.request.CategoryRequest;
import com.sundaymvp.invoice_api.dto.response.CategoryResponse;
import com.sundaymvp.invoice_api.entity.Category;

public class CategoryMapper {

    private CategoryMapper() {
    }

    public static Category toEntity(CategoryRequest request) {

        Category category = new Category();

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setStatus(request.getStatus());

        return category;
    }

    public static CategoryResponse toResponse(Category category) {

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getStatus()
        );
    }
}
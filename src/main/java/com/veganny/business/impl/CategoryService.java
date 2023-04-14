package com.veganny.business.impl;

import com.veganny.controller.DTO.CategoryRequest;
import com.veganny.controller.DTO.CategoryResponse;
import com.veganny.persistence.CategoryRepository;
import com.veganny.persistence.entity.CategoryEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The category was not found with id:" + id));
        return convertToCategoryResponse(categoryEntity);
    }

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryRequest.getName());
        categoryEntity.setDescription(categoryRequest.getDescription());
        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);
        return convertToCategoryResponse(savedCategoryEntity);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The category was not found with id:" + id));
        categoryEntity.setName(categoryRequest.getName());
        categoryEntity.setDescription(categoryRequest.getDescription());
        CategoryEntity updatedCategoryEntity = categoryRepository.save(categoryEntity);
        return convertToCategoryResponse(updatedCategoryEntity);
    }

    public void deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The category was not found with id:" + id));
        categoryRepository.deleteById(id);
    }

    private CategoryResponse convertToCategoryResponse(CategoryEntity categoryEntity) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(categoryEntity.getId());
        categoryResponse.setName(categoryEntity.getName());
        categoryResponse.setDescription(categoryEntity.getDescription());
        return categoryResponse;
    }
}
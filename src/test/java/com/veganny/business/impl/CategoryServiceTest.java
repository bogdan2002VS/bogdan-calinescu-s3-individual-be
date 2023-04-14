package com.veganny.business.impl;

import com.veganny.controller.DTO.CategoryRequest;
import com.veganny.controller.DTO.CategoryResponse;
import com.veganny.persistence.CategoryRepository;
import com.veganny.persistence.entity.CategoryEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Should throw an exception when the category is not found by id")
    void getCategoryByIdWhenCategoryNotFoundThenThrowException() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                EntityNotFoundException.class, () -> categoryService.getCategoryById(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Should return category by id when the category exists")
    void getCategoryByIdWhenCategoryExists() {
        Long categoryId = 1L;
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);
        categoryEntity.setName("Test Category");
        categoryEntity.setDescription("Test Description");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));

        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);

        assertNotNull(categoryResponse);
        assertEquals(categoryId, categoryResponse.getId());
        assertEquals(categoryEntity.getName(), categoryResponse.getName());
        assertEquals(categoryEntity.getDescription(), categoryResponse.getDescription());

        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Should create a new category and return the created category")
    void createCategory() {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Test Category");
        categoryRequest.setDescription("Test Description");

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName(categoryRequest.getName());
        categoryEntity.setDescription(categoryRequest.getDescription());

        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);

        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);

        assertEquals(categoryEntity.getId(), categoryResponse.getId());
        assertEquals(categoryEntity.getName(), categoryResponse.getName());
        assertEquals(categoryEntity.getDescription(), categoryResponse.getDescription());
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    @DisplayName("Should return all categories")
    void getAllCategories() {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setId(1L);
        categoryEntity1.setName("Category 1");
        categoryEntity1.setDescription("Description 1");
        categoryEntities.add(categoryEntity1);

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setId(2L);
        categoryEntity2.setName("Category 2");
        categoryEntity2.setDescription("Description 2");
        categoryEntities.add(categoryEntity2);

        when(categoryRepository.findAll()).thenReturn(categoryEntities);

        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();

        assertEquals(2, categoryResponses.size());
        assertEquals("Category 1", categoryResponses.get(0).getName());
        assertEquals("Description 1", categoryResponses.get(0).getDescription());
        assertEquals("Category 2", categoryResponses.get(1).getName());
        assertEquals("Description 2", categoryResponses.get(1).getDescription());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update the category when the category exists")
    void updateCategoryWhenCategoryExists() {
        Long categoryId = 1L;
        String updatedName = "Updated Category Name";
        String updatedDescription = "Updated Category Description";
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(categoryId);
        categoryRequest.setName(updatedName);
        categoryRequest.setDescription(updatedDescription);

        CategoryEntity existingCategoryEntity = new CategoryEntity();
        existingCategoryEntity.setId(categoryId);
        existingCategoryEntity.setName("Old Category Name");
        existingCategoryEntity.setDescription("Old Category Description");

        CategoryEntity updatedCategoryEntity = new CategoryEntity();
        updatedCategoryEntity.setId(categoryId);
        updatedCategoryEntity.setName(updatedName);
        updatedCategoryEntity.setDescription(updatedDescription);

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(existingCategoryEntity));
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(updatedCategoryEntity);

        CategoryResponse categoryResponse =
                categoryService.updateCategory(categoryId, categoryRequest);

        assertEquals(categoryId, categoryResponse.getId());
        assertEquals(updatedName, categoryResponse.getName());
        assertEquals(updatedDescription, categoryResponse.getDescription());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }
}
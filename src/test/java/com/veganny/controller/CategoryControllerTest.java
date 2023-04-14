package com.veganny.controller;

import com.veganny.business.CategoryService;
import com.veganny.business.dto.CategoryRequest;
import com.veganny.business.dto.CategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    @DisplayName("Should delete a category by id")
    void deleteCategory() {
        Long categoryId = 1L;

        categoryController.deleteCategory(categoryId);

        verify(categoryService, times(1)).deleteCategory(categoryId);
    }

    @Test
    @DisplayName("Should return category by id")
    void getCategoryById() {
        Long categoryId = 1L;
        CategoryResponse expectedCategoryResponse = new CategoryResponse();
        expectedCategoryResponse.setId(categoryId);
        expectedCategoryResponse.setName("Test Category");
        expectedCategoryResponse.setDescription("Test Description");

        when(categoryService.getCategoryById(categoryId)).thenReturn(expectedCategoryResponse);

        CategoryResponse actualCategoryResponse = categoryController.getCategoryById(categoryId);

        assertEquals(expectedCategoryResponse, actualCategoryResponse);
        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    @DisplayName("Should create a new category")
    void createCategory() {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Test Category");
        categoryRequest.setDescription("Test Category Description");

        CategoryResponse expectedCategoryResponse = new CategoryResponse();
        expectedCategoryResponse.setId(1L);
        expectedCategoryResponse.setName("Test Category");
        expectedCategoryResponse.setDescription("Test Category Description");

        when(categoryService.createCategory(categoryRequest)).thenReturn(expectedCategoryResponse);

        CategoryResponse actualCategoryResponse =
                categoryController.createCategory(categoryRequest);

        assertEquals(expectedCategoryResponse.getId(), actualCategoryResponse.getId());
        assertEquals(expectedCategoryResponse.getName(), actualCategoryResponse.getName());
        assertEquals(
                expectedCategoryResponse.getDescription(), actualCategoryResponse.getDescription());
        verify(categoryService, times(1)).createCategory(categoryRequest);
    }

    @Test
    @DisplayName("Should return all categories")
    void getAllCategories() {
        CategoryResponse category1 = new CategoryResponse();
        category1.setId(1L);
        category1.setName("Category 1");
        category1.setDescription("Description 1");

        CategoryResponse category2 = new CategoryResponse();
        category2.setId(2L);
        category2.setName("Category 2");
        category2.setDescription("Description 2");

        List<CategoryResponse> expectedCategories = List.of(category1, category2);
        when(categoryService.getAllCategories()).thenReturn(expectedCategories);

        List<CategoryResponse> actualCategories = categoryController.getAllCategories();

        assertEquals(expectedCategories, actualCategories);
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    @DisplayName("Should update an existing category")
    void updateCategory() {
        Long categoryId = 1L;
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(categoryId);
        categoryRequest.setName("Updated Category");
        categoryRequest.setDescription("Updated Category Description");

        CategoryResponse expectedCategoryResponse = new CategoryResponse();
        expectedCategoryResponse.setId(categoryId);
        expectedCategoryResponse.setName("Updated Category");
        expectedCategoryResponse.setDescription("Updated Category Description");

        when(categoryService.updateCategory(categoryId, categoryRequest))
                .thenReturn(expectedCategoryResponse);

        CategoryResponse actualCategoryResponse =
                categoryController.updateCategory(categoryId, categoryRequest);

        assertEquals(expectedCategoryResponse.getId(), actualCategoryResponse.getId());
        assertEquals(expectedCategoryResponse.getName(), actualCategoryResponse.getName());
        assertEquals(
                expectedCategoryResponse.getDescription(), actualCategoryResponse.getDescription());

        verify(categoryService, times(1)).updateCategory(categoryId, categoryRequest);
    }
}
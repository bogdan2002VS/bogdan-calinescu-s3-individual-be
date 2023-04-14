package com.veganny.business;

import com.veganny.persistence.CategoryRepository;
import com.veganny.persistence.entity.Category;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock private CategoryRepository categoryRepository;

    @InjectMocks private CategoryService categoryService;

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
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Fruits");
        category.setDescription("Fresh fruits");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);

        assertNotNull(categoryResponse);
        assertEquals(categoryId, categoryResponse.getId());
        assertEquals("Fruits", categoryResponse.getName());
        assertEquals("Fresh fruits", categoryResponse.getDescription());

        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Should create a new category and return the created category")
    void createCategory() {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Test Category");
        categoryRequest.setDescription("Test Description");

        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName(categoryRequest.getName());
        savedCategory.setDescription(categoryRequest.getDescription());

        when(categoryRepository.save(category)).thenReturn(savedCategory);

        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);

        assertEquals(savedCategory.getId(), categoryResponse.getId());
        assertEquals(savedCategory.getName(), categoryResponse.getName());
        assertEquals(savedCategory.getDescription(), categoryResponse.getDescription());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("Should return all categories")
    void getAllCategories() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");
        category1.setDescription("Description 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");
        category2.setDescription("Description 2");

        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();

        assertEquals(2, categoryResponses.size());
        assertEquals(category1.getId(), categoryResponses.get(0).getId());
        assertEquals(category1.getName(), categoryResponses.get(0).getName());
        assertEquals(category1.getDescription(), categoryResponses.get(0).getDescription());
        assertEquals(category2.getId(), categoryResponses.get(1).getId());
        assertEquals(category2.getName(), categoryResponses.get(1).getName());
        assertEquals(category2.getDescription(), categoryResponses.get(1).getDescription());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update the category when the category exists")
    void updateCategoryWhenCategoryExists() {
        Long categoryId = 1L;
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(categoryId);
        categoryRequest.setName("Updated Category");
        categoryRequest.setDescription("Updated Description");

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Old Category");
        existingCategory.setDescription("Old Description");

        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName(categoryRequest.getName());
        updatedCategory.setDescription(categoryRequest.getDescription());

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);

        CategoryResponse result = categoryService.updateCategory(categoryId, categoryRequest);

        assertEquals(categoryId, result.getId());
        assertEquals(categoryRequest.getName(), result.getName());
        assertEquals(categoryRequest.getDescription(), result.getDescription());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(existingCategory);
    }
}
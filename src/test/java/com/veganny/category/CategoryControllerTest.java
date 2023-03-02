package com.veganny.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    @DisplayName("Should throw an exception when the id is invalid")
    void updateCategoryWhenIdIsInvalidThenThrowException() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Test");
        category.setDescription("Test");

        when(categoryService.updateCategory(anyLong(), any()))
                .thenThrow(new IllegalArgumentException());

        // Act and assert
        assertThrows(
                IllegalArgumentException.class,
                () -> categoryController.updateCategory(1L, category));
    }

    @Test
    @DisplayName("Should return the updated category when the id is valid")
    void updateCategoryWhenIdIsValidThenReturnUpdatedCategory() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        category.setDescription("Description 1");

        Category categoryDetails = new Category();
        categoryDetails.setName("Category 1 updated");
        categoryDetails.setDescription("Description 1 updated");

        when(categoryService.updateCategory(anyLong(), any())).thenReturn(category);

        // Act
        Category updatedCategory = categoryController.updateCategory(1L, categoryDetails);

        // Assert
        assertEquals(category, updatedCategory);
    }

    @Test
    @DisplayName("Should return the created category")
    void createCategoryShouldReturnTheCreatedCategory() {
        // Arrange
        Category category = new Category();
        category.setName("Test");
        category.setDescription("Test");

        when(categoryService.createCategory(category)).thenReturn(category);

        // Act
        Category createdCategory = categoryController.createCategory(category);

        // Assert
        assertEquals(category, createdCategory);
    }

    @Test
    @DisplayName("Should delete the category when the id is valid")
    void deleteCategoryWhenIdIsValid() {
        // Arrange
        Long id = 1L;

        // Act
        categoryController.deleteCategory(id);

        // Assert
        verify(categoryService, times(1)).deleteCategory(id);
    }

    @Test
    @DisplayName("Should return all categories")
    void getAllCategoriesShouldReturnAllCategories() {
        // Arrange
        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryService.getAllCategories()).thenReturn(categories);

        // Act
        List<Category> result = categoryController.getAllCategories();

        // Assert
        assertEquals(categories, result);
    }

    @Test
    @DisplayName("Should throw an exception when the id is invalid")
    void getCategoryByIdWhenIdIsInvalidThenThrowException() {
        // Arrange
        Long id = 1L;
        when(categoryService.getCategoryById(id)).thenThrow(new IllegalArgumentException());

        // Act and assert
        assertThrows(IllegalArgumentException.class, () -> categoryController.getCategoryById(id));
    }

    @Test
    @DisplayName("Should return the category when the id is valid")
    void getCategoryByIdWhenIdIsValid() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        category.setDescription("Description 1");

        when(categoryService.getCategoryById(1L)).thenReturn(category);

        // Act
        Category result = categoryController.getCategoryById(1L);

        // Assert
        assertEquals(category, result);
    }

}
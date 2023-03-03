package com.veganny.category;

import com.veganny.business.CategoryService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Should save the category")
    void createCategoryShouldSaveTheCategory() {
        Category category = new Category();
        category.setName("Category 1");
        category.setDescription("Category 1 description");

        when(categoryRepository.save(category)).thenReturn(category);

        Category savedCategory = categoryService.createCategory(category);

        assertEquals(category, savedCategory);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("Should return the category when the id is valid")
    void getCategoryByIdWhenIdIsValid() {
        Long id = 1L;
        Category category = new Category();
        category.setId(id);
        category.setName("Test");
        category.setDescription("Test");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(id);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should throw an exception when the id is invalid")
    void getCategoryByIdWhenIdIsInvalidThenThrowException() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception =
                assertThrows(
                        EntityNotFoundException.class, () -> categoryService.getCategoryById(id));

        assertEquals("The category was not found with id:" + id, exception.getMessage());
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should throw an exception when the id is invalid")
    void deleteCategoryWhenIdIsInvalidThenThrowException() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategory(id));
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should delete the category when the id is valid")
    void deleteCategoryWhenIdIsValid() {
        Long id = 1L;
        Category category = new Category();
        category.setId(id);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(id);

        verify(categoryRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should throw an exception when the id is invalid")
    void updateCategoryWhenIdIsInvalidThenThrowException() {
        Long id = 1L;
        Category categoryDetails = new Category();
        categoryDetails.setName("Category 1");
        categoryDetails.setDescription("Description 1");

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.updateCategory(id, categoryDetails));
        verify(categoryRepository, times(1)).findById(id);
    }

}
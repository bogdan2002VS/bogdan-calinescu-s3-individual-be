package com.veganny.category;

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
    }

    @Test
    @DisplayName("Should throw an exception when the id is invalid")
    void deleteCategoryWhenIdIsInvalidThenThrowException() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategory(id));
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
    }

    @Test
    @DisplayName("Should update the category when the id is valid")
    void updateCategoryWhenIdIsValid() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        category.setDescription("Description 1");

        Category categoryDetails = new Category();
        categoryDetails.setName("Category 2");
        categoryDetails.setDescription("Description 2");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        Category updatedCategory = categoryService.updateCategory(1L, categoryDetails);

        assertEquals("Category 2", updatedCategory.getName());
        assertEquals("Description 2", updatedCategory.getDescription());
    }

    @Test
    @DisplayName("Should return all categories")
    void getAllCategoriesShouldReturnAllCategories() {
        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(categories, result);
    }
}
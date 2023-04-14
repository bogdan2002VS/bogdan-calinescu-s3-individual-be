package com.veganny.business.impl;

import com.veganny.business.exception.NotFoundException;
import com.veganny.persistence.RecipeRepository;
import com.veganny.persistence.entity.RecipeEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeService recipeService;

    @Test
    @DisplayName("Should throw NotFoundException when the id is not found")
    void deleteRecipeWhenIdIsNotFoundThenThrowNotFoundException() {
        Long id = 1L;
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> recipeService.deleteRecipe(id));
        verify(recipeRepository, times(1)).findById(id);
        verify(recipeRepository, never()).delete(any(RecipeEntity.class));
    }

    @Test
    @DisplayName("Should delete the recipe when the id is valid")
    void deleteRecipeWhenIdIsValid() {
        Long id = 1L;
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(id);

        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipeEntity));

        recipeService.deleteRecipe(id);

        verify(recipeRepository, times(1)).findById(id);
        verify(recipeRepository, times(1)).delete(recipeEntity);
    }

    @Test
    @DisplayName("Should throw NotFoundException when the recipe id is not found")
    void updateRecipeWhenIdNotFoundThenThrowNotFoundException() {
        Long id = 1L;
        RecipeEntity recipeEntityDetails = new RecipeEntity();
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception =
                assertThrows(
                        NotFoundException.class,
                        () -> recipeService.updateRecipe(id, recipeEntityDetails));

        assertEquals("Recipe was not found with id:" + id, exception.getMessage());
        verify(recipeRepository, times(1)).findById(id);
        verify(recipeRepository, times(0)).save(any(RecipeEntity.class));
    }

    @Test
    @DisplayName("Should update the recipe with the given id and new details")
    void updateRecipeWithGivenIdAndNewDetails() {
        Long id = 1L;
        RecipeEntity existingRecipe =
                new RecipeEntity(
                        id,
                        "Old Recipe",
                        null,
                        null,
                        "Old Nutritional Score",
                        "Old Description",
                        "Old Prep Time",
                        "Old Cook Time");
        RecipeEntity updatedRecipeDetails =
                new RecipeEntity(
                        null,
                        "New Recipe",
                        null,
                        null,
                        "New Nutritional Score",
                        "New Description",
                        "New Prep Time",
                        "New Cook Time");
        RecipeEntity updatedRecipe =
                new RecipeEntity(
                        id,
                        "New Recipe",
                        null,
                        null,
                        "New Nutritional Score",
                        "New Description",
                        "New Prep Time",
                        "New Cook Time");

        when(recipeRepository.findById(id)).thenReturn(Optional.of(existingRecipe));
        when(recipeRepository.save(any(RecipeEntity.class))).thenReturn(updatedRecipe);

        RecipeEntity result = recipeService.updateRecipe(id, updatedRecipeDetails);

        assertEquals(updatedRecipe, result);
        verify(recipeRepository).findById(id);
        verify(recipeRepository).save(any(RecipeEntity.class));
    }

    @Test
    @DisplayName("Should create and save a new recipe successfully")
    void createRecipeSuccessfully() {
        RecipeEntity recipeEntity =
                new RecipeEntity(
                        1L,
                        "Test Recipe",
                        null,
                        null,
                        "A",
                        "Test description",
                        "10 mins",
                        "20 mins");
        when(recipeRepository.save(any(RecipeEntity.class))).thenReturn(recipeEntity);

        RecipeEntity createdRecipe = recipeService.createRecipe(recipeEntity);

        assertEquals(recipeEntity.getId(), createdRecipe.getId());
        assertEquals(recipeEntity.getName(), createdRecipe.getName());
        assertEquals(recipeEntity.getNutritionalScore(), createdRecipe.getNutritionalScore());
        assertEquals(recipeEntity.getDescription(), createdRecipe.getDescription());
        assertEquals(recipeEntity.getPrepTime(), createdRecipe.getPrepTime());
        assertEquals(recipeEntity.getCookTime(), createdRecipe.getCookTime());
        verify(recipeRepository, times(1)).save(recipeEntity);
    }

    @Test
    @DisplayName("Should throw NotFoundException when the id is not found")
    void getRecipeByIdWhenIdIsNotFoundThenThrowNotFoundException() {
        Long id = 1L;
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeService.getRecipeById(id));

        // Verify
        verify(recipeRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return the recipe when the id is valid")
    void getRecipeByIdWhenIdIsValid() {
        Long id = 1L;
        RecipeEntity expectedRecipe =
                new RecipeEntity(
                        id, "Test Recipe", null, null, "A", "Test description", "30 min", "20 min");
        when(recipeRepository.findById(id)).thenReturn(Optional.of(expectedRecipe));

        RecipeEntity actualRecipe = recipeService.getRecipeById(id);

        assertEquals(expectedRecipe, actualRecipe);
        verify(recipeRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return all recipes")
    void getAllRecipes() {
        List<RecipeEntity> expectedRecipes = new ArrayList<>();
        expectedRecipes.add(
                new RecipeEntity(
                        1L, "Recipe 1", null, null, "A", "Description 1", "10 min", "20 min"));
        expectedRecipes.add(
                new RecipeEntity(
                        2L, "Recipe 2", null, null, "B", "Description 2", "15 min", "25 min"));
        when(recipeRepository.findAll()).thenReturn(expectedRecipes);

        List<RecipeEntity> actualRecipes = recipeService.getAllRecipes();

        assertEquals(expectedRecipes, actualRecipes);
        verify(recipeRepository, times(1)).findAll();
    }
}
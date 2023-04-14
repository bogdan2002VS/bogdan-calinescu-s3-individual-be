package com.veganny.business.impl;

import com.veganny.persistence.RecipeIngredientId;
import com.veganny.persistence.RecipeIngredientRepository;
import com.veganny.persistence.entity.IngredientEntity;
import com.veganny.persistence.entity.RecipeEntity;
import com.veganny.persistence.entity.RecipeIngredientEntity;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeIngredientServiceTest {

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    @InjectMocks
    private RecipeIngredientService recipeIngredientService;

    @Test
    @DisplayName("Should throw an exception when the id is not found")
    void deleteRecipeIngredientWhenIdNotFoundThenThrowException() {
        RecipeIngredientId id = new RecipeIngredientId(1L, 2L);
        when(recipeIngredientRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        EntityNotFoundException exception =
                assertThrows(
                        EntityNotFoundException.class,
                        () -> recipeIngredientService.deleteRecipeIngredient(id));
        assertEquals("RecipeIngredient not found with id: " + id, exception.getMessage());
        verify(recipeIngredientRepository, times(0)).deleteById(id);
    }

    @Test
    @DisplayName("Should delete the recipe ingredient when the id is valid")
    void deleteRecipeIngredientWhenIdIsValid() {
        RecipeIngredientId id = new RecipeIngredientId(1L, 2L);
        RecipeIngredientEntity recipeIngredientEntity = new RecipeIngredientEntity();
        when(recipeIngredientRepository.findById(id))
                .thenReturn(Optional.of(recipeIngredientEntity));

        recipeIngredientService.deleteRecipeIngredient(id);

        verify(recipeIngredientRepository, times(1)).findById(id);
        verify(recipeIngredientRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should throw an exception when the recipe ingredient id is not found")
    void updateRecipeIngredientWhenIdNotFoundThenThrowException() {
        RecipeIngredientId id = new RecipeIngredientId(1L, 2L);
        RecipeIngredientEntity recipeIngredientEntityDetails = new RecipeIngredientEntity();
        when(recipeIngredientRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception =
                assertThrows(
                        EntityNotFoundException.class,
                        () ->
                                recipeIngredientService.updateRecipeIngredient(
                                        id, recipeIngredientEntityDetails));

        assertEquals("RecipeIngredient not found with id: " + id, exception.getMessage());
        verify(recipeIngredientRepository, times(1)).findById(id);
        verify(recipeIngredientRepository, times(0)).save(any(RecipeIngredientEntity.class));
    }

    @Test
    @DisplayName("Should update the recipe ingredient with the given id and new quantity")
    void updateRecipeIngredientWithGivenIdAndNewQuantity() {
        RecipeIngredientId id = new RecipeIngredientId(1L, 2L);
        RecipeEntity recipeEntity = new RecipeEntity();
        IngredientEntity ingredientEntity = new IngredientEntity();
        RecipeIngredientEntity existingRecipeIngredientEntity =
                new RecipeIngredientEntity(recipeEntity, ingredientEntity, 2);
        RecipeIngredientEntity updatedRecipeIngredientEntity =
                new RecipeIngredientEntity(recipeEntity, ingredientEntity, 3);
        RecipeIngredientEntity expectedRecipeIngredientEntity =
                new RecipeIngredientEntity(recipeEntity, ingredientEntity, 3);

        when(recipeIngredientRepository.findById(id))
                .thenReturn(Optional.of(existingRecipeIngredientEntity));
        when(recipeIngredientRepository.save(any(RecipeIngredientEntity.class)))
                .thenReturn(updatedRecipeIngredientEntity);

        RecipeIngredientEntity result =
                recipeIngredientService.updateRecipeIngredient(id, updatedRecipeIngredientEntity);

        assertEquals(expectedRecipeIngredientEntity.getQuantity(), result.getQuantity());
        verify(recipeIngredientRepository, times(1)).findById(id);
        verify(recipeIngredientRepository, times(1)).save(any(RecipeIngredientEntity.class));
    }

    @Test
    @DisplayName("Should create and save a new recipe ingredient")
    void createRecipeIngredient() {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(1L);
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setId(1L);
        RecipeIngredientEntity recipeIngredientEntity = new RecipeIngredientEntity();
        recipeIngredientEntity.setRecipeEntity(recipeEntity);
        recipeIngredientEntity.setIngredientEntity(ingredientEntity);
        recipeIngredientEntity.setQuantity(2);

        RecipeIngredientId recipeIngredientId = new RecipeIngredientId(1L, 1L);

        when(recipeIngredientRepository.save(any(RecipeIngredientEntity.class)))
                .thenReturn(recipeIngredientEntity);

        RecipeIngredientEntity createdRecipeIngredient =
                recipeIngredientService.createRecipeIngredient(recipeIngredientEntity);

        assertEquals(recipeIngredientEntity, createdRecipeIngredient);

        assertEquals(2, createdRecipeIngredient.getQuantity());

        verify(recipeIngredientRepository, times(1)).save(recipeIngredientEntity);
    }

    @Test
    @DisplayName("Should throw an exception when the id is not found")
    void getRecipeIngredientByIdWhenIdNotFoundThenThrowException() {
        RecipeIngredientId id = new RecipeIngredientId(1L, 2L);
        when(recipeIngredientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> recipeIngredientService.getRecipeIngredientById(id));
        verify(recipeIngredientRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return the recipe ingredient when the id is valid")
    void getRecipeIngredientByIdWhenIdIsValid() {
        RecipeIngredientId id = new RecipeIngredientId(1L, 2L);
        RecipeEntity recipeEntity = new RecipeEntity();
        IngredientEntity ingredientEntity = new IngredientEntity();
        RecipeIngredientEntity expectedRecipeIngredientEntity =
                new RecipeIngredientEntity(recipeEntity, ingredientEntity, 3);

        when(recipeIngredientRepository.findById(id))
                .thenReturn(Optional.of(expectedRecipeIngredientEntity));

        RecipeIngredientEntity actualRecipeIngredientEntity =
                recipeIngredientService.getRecipeIngredientById(id);

        assertEquals(expectedRecipeIngredientEntity, actualRecipeIngredientEntity);
        verify(recipeIngredientRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return all recipe ingredients")
    void getAllRecipeIngredients() {
        List<RecipeIngredientEntity> expectedRecipeIngredients = new ArrayList<>();
        RecipeEntity recipe = new RecipeEntity();
        IngredientEntity ingredient = new IngredientEntity();
        RecipeIngredientId id1 = new RecipeIngredientId(1L, 1L);
        RecipeIngredientId id2 = new RecipeIngredientId(1L, 2L);
        expectedRecipeIngredients.add(new RecipeIngredientEntity(recipe, ingredient, 2));
        expectedRecipeIngredients.add(new RecipeIngredientEntity(recipe, ingredient, 3));

        when(recipeIngredientRepository.findAll()).thenReturn(expectedRecipeIngredients);

        List<RecipeIngredientEntity> actualRecipeIngredients =
                recipeIngredientService.getAllRecipeIngredients();

        assertEquals(expectedRecipeIngredients, actualRecipeIngredients);
        verify(recipeIngredientRepository, times(1)).findAll();
    }
}
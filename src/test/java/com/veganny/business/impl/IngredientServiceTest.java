package com.veganny.business.impl;

import com.veganny.persistence.IngredientRepository;
import com.veganny.persistence.entity.IngredientEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    @Test
    @DisplayName("Should throw an exception when the id is not found")
    void deleteIngredientWhenIdIsNotFoundThenThrowException() {
        Long id = 1L;
        when(ingredientRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        EntityNotFoundException exception =
                assertThrows(
                        EntityNotFoundException.class,
                        () -> ingredientService.deleteIngredient(id));
        assertEquals("Ingredient was not found with id:" + id, exception.getMessage());
        verify(ingredientRepository, times(0)).deleteById(id);
    }

    @Test
    @DisplayName("Should delete the ingredient when the id is valid")
    void deleteIngredientWhenIdIsValid() {
        Long id = 1L;
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setId(id);
        ingredientEntity.setName("Test Ingredient");

        when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredientEntity));

        ingredientService.deleteIngredient(id);

        verify(ingredientRepository, times(1)).findById(id);
        verify(ingredientRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should throw an exception when the id is not found")
    void updateIngredientWhenIdIsNotFoundThenThrowException() {
        Long id = 1L;
        IngredientEntity ingredientEntityDetails = new IngredientEntity();
        ingredientEntityDetails.setName("Updated Ingredient");

        when(ingredientRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception =
                assertThrows(
                        EntityNotFoundException.class,
                        () -> ingredientService.updateIngredient(id, ingredientEntityDetails));

        assertEquals("Ingredient was not found with id:" + id, exception.getMessage());
        verify(ingredientRepository, times(1)).findById(id);
        verify(ingredientRepository, times(0)).save(any(IngredientEntity.class));
    }

    @Test
    @DisplayName("Should update the ingredient when the id is valid")
    void updateIngredientWhenIdIsValid() {
        Long id = 1L;
        IngredientEntity existingIngredient = new IngredientEntity();
        existingIngredient.setId(id);
        existingIngredient.setName("Old Ingredient");

        IngredientEntity updatedIngredientDetails = new IngredientEntity();
        updatedIngredientDetails.setName("New Ingredient");

        IngredientEntity updatedIngredient = new IngredientEntity();
        updatedIngredient.setId(id);
        updatedIngredient.setName("New Ingredient");

        when(ingredientRepository.findById(id)).thenReturn(Optional.of(existingIngredient));
        when(ingredientRepository.save(any(IngredientEntity.class))).thenReturn(updatedIngredient);

        IngredientEntity result = ingredientService.updateIngredient(id, updatedIngredientDetails);

        assertEquals(updatedIngredient, result);
        verify(ingredientRepository).findById(id);
        verify(ingredientRepository).save(any(IngredientEntity.class));
    }

    @Test
    @DisplayName("Should create and return a new ingredient")
    void createIngredient() {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setId(1L);
        ingredientEntity.setName("Test Ingredient");

        when(ingredientRepository.save(any(IngredientEntity.class))).thenReturn(ingredientEntity);

        IngredientEntity createdIngredient = ingredientService.createIngredient(ingredientEntity);

        assertEquals(ingredientEntity.getId(), createdIngredient.getId());
        assertEquals(ingredientEntity.getName(), createdIngredient.getName());
        verify(ingredientRepository, times(1)).save(ingredientEntity);
    }

    @Test
    @DisplayName("Should throw an exception when the id is not found")
    void getIngredientByIdWhenIdIsNotFoundThenThrowException() {
        Long id = 1L;
        when(ingredientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ingredientService.getIngredientById(id));
        verify(ingredientRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return the ingredient when the id is valid")
    void getIngredientByIdWhenIdIsValid() {
        Long ingredientId = 1L;
        IngredientEntity expectedIngredient = new IngredientEntity();
        expectedIngredient.setId(ingredientId);
        expectedIngredient.setName("Test Ingredient");

        when(ingredientRepository.findById(ingredientId))
                .thenReturn(Optional.of(expectedIngredient));

        IngredientEntity actualIngredient = ingredientService.getIngredientById(ingredientId);

        assertEquals(expectedIngredient, actualIngredient);
        verify(ingredientRepository, times(1)).findById(ingredientId);
    }

    @Test
    @DisplayName("Should return all ingredients")
    void getAllIngredients() {
        IngredientEntity ingredient1 = new IngredientEntity();
        ingredient1.setId(1L);
        ingredient1.setName("Ingredient 1");

        IngredientEntity ingredient2 = new IngredientEntity();
        ingredient2.setId(2L);
        ingredient2.setName("Ingredient 2");

        List<IngredientEntity> expectedIngredients = List.of(ingredient1, ingredient2);

        when(ingredientRepository.findAll()).thenReturn(expectedIngredients);

        List<IngredientEntity> actualIngredients = ingredientService.getAllIngredients();

        assertEquals(expectedIngredients, actualIngredients);
        verify(ingredientRepository, times(1)).findAll();
    }
}
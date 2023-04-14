package com.veganny.business;

import com.veganny.persistence.IngredientRepository;
import com.veganny.persistence.entity.Ingredient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    @Test
    @DisplayName("Should return all ingredients from the repository")
    void getAllIngredients() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setName("Ingredient 1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        ingredient2.setName("Ingredient 2");

        List<Ingredient> expectedIngredients = Arrays.asList(ingredient1, ingredient2);
        when(ingredientRepository.findAll()).thenReturn(expectedIngredients);

        List<Ingredient> actualIngredients = ingredientService.getAllIngredients();

        assertEquals(
                expectedIngredients,
                actualIngredients,
                "The returned ingredients should match the expected ingredients");
        verify(ingredientRepository, times(1)).findAll();
    }
}
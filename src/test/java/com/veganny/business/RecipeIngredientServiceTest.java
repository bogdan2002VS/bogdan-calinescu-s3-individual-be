package com.veganny.business;

import com.veganny.persistence.RecipeIngredientRepository;
import com.veganny.persistence.entity.RecipeIngredient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeIngredientServiceTest {

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    @InjectMocks
    private RecipeIngredientService recipeIngredientService;

    @Test
    @DisplayName("Should return all recipe ingredients")
    void getAllRecipeIngredients() {
        List<RecipeIngredient> expectedRecipeIngredients = new ArrayList<>();
        expectedRecipeIngredients.add(new RecipeIngredient());
        expectedRecipeIngredients.add(new RecipeIngredient());
        when(recipeIngredientRepository.findAll()).thenReturn(expectedRecipeIngredients);

        List<RecipeIngredient> actualRecipeIngredients =
                recipeIngredientService.getAllRecipeIngredients();

        assertEquals(expectedRecipeIngredients, actualRecipeIngredients);
        verify(recipeIngredientRepository, times(1)).findAll();
    }
}
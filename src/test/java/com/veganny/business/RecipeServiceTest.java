package com.veganny.business;

import com.veganny.persistence.RecipeRepository;
import com.veganny.persistence.entity.Recipe;
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
class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeService recipeService;

    @Test
    @DisplayName("Should return all recipes")
    void getAllRecipes() {
        Recipe recipe1 =
                new Recipe(
                        1L,
                        "Recipe1",
                        null,
                        "Cuisine1",
                        "Score1",
                        "Description1",
                        "PrepTime1",
                        "CookTime1");
        Recipe recipe2 =
                new Recipe(
                        2L,
                        "Recipe2",
                        null,
                        "Cuisine2",
                        "Score2",
                        "Description2",
                        "PrepTime2",
                        "CookTime2");
        List<Recipe> expectedRecipes = Arrays.asList(recipe1, recipe2);

        when(recipeRepository.findAll()).thenReturn(expectedRecipes);

        List<Recipe> actualRecipes = recipeService.getAllRecipes();

        assertEquals(
                expectedRecipes,
                actualRecipes,
                "Returned recipes should match the expected recipes");
        verify(recipeRepository, times(1)).findAll();
    }
}
package com.veganny.controller;

import com.veganny.business.service.impl.RecipeService;
import com.veganny.configuration.security.isauthenticated.IsAuthenticated;
import com.veganny.domain.Recipe;
import com.veganny.persistence.entity.RecipeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    private RecipeController recipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController(recipeService);
    }

    @Test
    void getAllRecipes() {
        List<Recipe> expectedRecipes = new ArrayList<>();
        when(recipeService.getAllRecipes()).thenReturn(expectedRecipes);

        List<Recipe> result = recipeController.getAllRecipes();

        assertEquals(expectedRecipes, result);
        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    void getRecipeById() {
        Long recipeId = 1L;
        Recipe expectedRecipe = new Recipe();
        when(recipeService.getRecipeById(recipeId)).thenReturn(expectedRecipe);

        Recipe result = recipeController.getRecipeById(recipeId);

        assertEquals(expectedRecipe, result);
        verify(recipeService, times(1)).getRecipeById(recipeId);
    }

    @Test
    void createRecipe() {
        Recipe recipe = new Recipe();
        RecipeEntity expectedRecipeEntity = new RecipeEntity();
        when(recipeService.createRecipe(recipe)).thenReturn(expectedRecipeEntity);

        RecipeEntity result = recipeController.createRecipe(recipe);

        assertEquals(expectedRecipeEntity, result);
        verify(recipeService, times(1)).createRecipe(recipe);
    }

    @Test
    void updateRecipe() {
        Long id = 1L;
        Recipe recipe = new Recipe();
        RecipeEntity expectedRecipeEntity = new RecipeEntity();
        when(recipeService.updateRecipe(id, recipe)).thenReturn(expectedRecipeEntity);

        RecipeEntity result = recipeController.updateRecipe(id, recipe);

        assertEquals(expectedRecipeEntity, result);
        verify(recipeService, times(1)).updateRecipe(id, recipe);
    }

    @Test
    void deleteRecipe() {
        Long id = 1L;

        recipeController.deleteRecipe(id);

        verify(recipeService, times(1)).deleteRecipe(id);
    }

    @Test
    void searchRecipes() {
        String title = "Title";
        String mealType = "Meal";
        Integer caloriesFrom = 100;
        Integer caloriesTo = 200;
        List<Recipe> expectedRecipes = new ArrayList<>();
        when(recipeService.searchRecipes(title, mealType, caloriesFrom, caloriesTo)).thenReturn(expectedRecipes);

        ResponseEntity<List<Recipe>> result = recipeController.searchRecipes(title, mealType, caloriesFrom, caloriesTo);

        assertEquals(expectedRecipes, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
        verify(recipeService, times(1)).searchRecipes(title, mealType, caloriesFrom, caloriesTo);
    }
}

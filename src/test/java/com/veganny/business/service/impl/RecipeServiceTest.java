package com.veganny.business.service.impl;

import com.veganny.persistence.JPARecipeRepository;
import com.veganny.persistence.entity.RecipeEntity;
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
class RecipeServiceTest {

    @Mock
    private JPARecipeRepository JPARecipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    @DisplayName("Should return all recipes")
    void getAllRecipes() { // create a list of RecipeEntity objects
        List<RecipeEntity> recipeEntities = new ArrayList<>();
        recipeEntities.add(
                new RecipeEntity(1L, "Recipe 1", "A", "Description 1", "10 mins", "20 mins"));
        recipeEntities.add(
                new RecipeEntity(2L, "Recipe 2", "B", "Description 2", "15 mins", "25 mins"));

        // mock the findAll method of JPARecipeRepository to return the list of RecipeEntity objects
        when(JPARecipeRepository.findAll()).thenReturn(recipeEntities);

        // call the getAllRecipes method of RecipeService
        List<RecipeEntity> result = recipeService.getAllRecipes();

        // verify that the findAll method of JPARecipeRepository was called once
        verify(JPARecipeRepository, times(1)).findAll();

        // verify that the result returned by getAllRecipes method of RecipeService is equal to the
        // list of RecipeEntity objects
        assertEquals(recipeEntities, result);
    }
}
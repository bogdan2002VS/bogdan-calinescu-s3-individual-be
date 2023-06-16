package com.veganny.business.service.impl;

import com.veganny.business.exception.NotFoundException;
import com.veganny.domain.Recipe;
import com.veganny.persistence.RecipeRepository;
import com.veganny.persistence.entity.RecipeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private RecipeRepository recipeRepository;
    private static final String RECIPE_NOT_FOUND_MESSAGE = "Recipe was not found with id: ";

    @PersistenceContext
    private EntityManager entityManager;

    public List<Recipe> getAllRecipes() {
        List<RecipeEntity> recipeEntities = recipeRepository.findAll();
        return recipeEntities.stream()
                .map(this::convertToDto)
                .toList();
    }


    public Recipe getRecipeById(Long id) {
        RecipeEntity recipeEntity = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RECIPE_NOT_FOUND_MESSAGE + id));
        return convertToDto(recipeEntity);
    }
    public RecipeEntity createRecipe(Recipe recipe) {
        RecipeEntity recipeEntity = convertToEntity(recipe);
        return recipeRepository.save(recipeEntity);
    }

    public RecipeEntity updateRecipe(Long id, Recipe recipe) {
        RecipeEntity existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RECIPE_NOT_FOUND_MESSAGE + id));
        existingRecipe.setId(id);
        existingRecipe.setTitle(recipe.getTitle());
        existingRecipe.setCalories(recipe.getCalories());
        existingRecipe.setIngredients(recipe.getIngredients());
        existingRecipe.setImage(recipe.getImage());
        existingRecipe.setMealType(recipe.getMealType());
        return recipeRepository.save(existingRecipe);
    }

    public void deleteRecipe(Long id) {
        RecipeEntity recipeEntity = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RECIPE_NOT_FOUND_MESSAGE + id));
        recipeRepository.delete(recipeEntity);
    }

    public List<Recipe> searchRecipes(String titleQuery, String mealTypeQuery, Integer caloriesFrom, Integer caloriesTo) {
        return recipeRepository.searchRecipes(titleQuery, mealTypeQuery, caloriesFrom, caloriesTo)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private Recipe convertToDto(RecipeEntity recipeEntity) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeEntity.getId());
        recipe.setTitle(recipeEntity.getTitle());
        recipe.setCalories(recipeEntity.getCalories());
        recipe.setIngredients(recipeEntity.getIngredients());
        recipe.setImage(recipeEntity.getImage());
        recipe.setMealType(recipeEntity.getMealType());
        return recipe;
    }

    private RecipeEntity convertToEntity(Recipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(recipe.getId());
        recipeEntity.setTitle(recipe.getTitle());
        recipeEntity.setCalories(recipe.getCalories());
        recipeEntity.setIngredients(recipe.getIngredients());
        recipeEntity.setImage(recipe.getImage());
        recipeEntity.setMealType(recipe.getMealType());
        return recipeEntity;
    }

}

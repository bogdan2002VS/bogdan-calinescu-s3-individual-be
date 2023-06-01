package com.veganny.business.service.impl;

import com.veganny.business.exception.NotFoundException;
import com.veganny.domain.Recipe;
import com.veganny.persistence.entity.RecipeEntity;
import com.veganny.persistence.entity.ReviewEntity;
import com.veganny.persistence.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {

    private RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        List<RecipeEntity> recipeEntities = recipeRepository.findAll();
        return recipeEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Recipe getRecipeById(Long id) {
        RecipeEntity recipeEntity = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe was not found with id: " + id));
        return convertToDto(recipeEntity);
    }

    public RecipeEntity createRecipe(Recipe recipe) {
        RecipeEntity recipeEntity = convertToEntity(recipe);
        return recipeRepository.save(recipeEntity);
    }

    public RecipeEntity updateRecipe(Long id, Recipe recipe) {
        RecipeEntity existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe was not found with id: " + id));
        existingRecipe.setTitle(recipe.getTitle());
        existingRecipe.setCalories(recipe.getCalories());
        existingRecipe.setIngredients(recipe.getIngredients());
        existingRecipe.setImage(recipe.getImage());
        existingRecipe.setReviews(recipe.getReviews().stream()
                .map(this::convertReviewToEntity)
                .collect(Collectors.toList()));
        return recipeRepository.save(existingRecipe);
    }

    public void deleteRecipe(Long id) {
        RecipeEntity recipeEntity = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe was not found with id: " + id));
        recipeRepository.delete(recipeEntity);
    }

    private Recipe convertToDto(RecipeEntity recipeEntity) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeEntity.getId());
        recipe.setTitle(recipeEntity.getTitle());
        recipe.setCalories(recipeEntity.getCalories());
        recipe.setIngredients(recipeEntity.getIngredients());
        recipe.setImage(recipeEntity.getImage());
        recipe.setReviews(recipeEntity.getReviews());
        return recipe;
    }

    private RecipeEntity convertToEntity(Recipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(recipe.getId());
        recipeEntity.setTitle(recipe.getTitle());
        recipeEntity.setCalories(recipe.getCalories());
        recipeEntity.setIngredients(recipe.getIngredients());
        recipeEntity.setImage(recipe.getImage());
        recipeEntity.setReviews(recipe.getReviews());
        return recipeEntity;
    }

    private ReviewEntity convertReviewToEntity(ReviewEntity review) {
        // No conversion needed since the review is already an entity
        return review;
    }
}

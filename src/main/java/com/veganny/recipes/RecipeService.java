package com.veganny.recipes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe was not found with id:" + id));
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long id, Recipe recipeDetails) {
        Recipe recipe = getRecipeById(id);

        recipe.setName(recipeDetails.getName());
        recipe.setCategory(recipeDetails.getCategory());
        recipe.setCuisine(recipeDetails.getCuisine());
        recipe.setNutritionalScore(recipeDetails.getNutritionalScore());
        recipe.setDescription(recipeDetails.getDescription());
        recipe.setPrepTime(recipeDetails.getPrepTime());
        recipe.setCookTime(recipeDetails.getCookTime());

        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = getRecipeById(id);
        recipeRepository.delete(recipe);
    }

}
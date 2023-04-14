package com.veganny.controller;

import com.veganny.business.impl.RecipeIngredientService;
import com.veganny.persistence.RecipeIngredientId;
import com.veganny.persistence.entity.RecipeIngredientEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipeIngredients")
@AllArgsConstructor
public class RecipeIngredientController {

    private RecipeIngredientService recipeIngredientService;

    @GetMapping
    public List<RecipeIngredientEntity> getAllRecipeIngredients() {
        return recipeIngredientService.getAllRecipeIngredients();
    }

    @GetMapping("/{recipeId}/{ingredientId}")
    public RecipeIngredientEntity getRecipeIngredientById(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        RecipeIngredientId id = new RecipeIngredientId(recipeId, ingredientId);
        return recipeIngredientService.getRecipeIngredientById(id);
    }

    @PostMapping
    public RecipeIngredientEntity createRecipeIngredient(@RequestBody RecipeIngredientEntity recipeIngredientEntity) {
        return recipeIngredientService.createRecipeIngredient(recipeIngredientEntity);
    }

    @PutMapping("/{recipeId}/{ingredientId}")
    public RecipeIngredientEntity updateRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, @RequestBody RecipeIngredientEntity recipeIngredientEntityDetails) {
        RecipeIngredientId id = new RecipeIngredientId(recipeId, ingredientId);
        return recipeIngredientService.updateRecipeIngredient(id, recipeIngredientEntityDetails);
    }

    @DeleteMapping("/{recipeId}/{ingredientId}")
    public void deleteRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        RecipeIngredientId id = new RecipeIngredientId(recipeId, ingredientId);
        recipeIngredientService.deleteRecipeIngredient(id);
    }

}

package com.veganny.controller;

import com.veganny.business.RecipeIngredientService;
import com.veganny.persistence.RecipeIngredientId;
import com.veganny.persistence.entity.RecipeIngredient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipeIngredients")
@AllArgsConstructor
public class RecipeIngredientController {

    private RecipeIngredientService recipeIngredientService;

    @GetMapping
    public List<RecipeIngredient> getAllRecipeIngredients() {
        return recipeIngredientService.getAllRecipeIngredients();
    }

    @GetMapping("/{recipeId}/{ingredientId}")
    public RecipeIngredient getRecipeIngredientById(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        RecipeIngredientId id = new RecipeIngredientId(recipeId, ingredientId);
        return recipeIngredientService.getRecipeIngredientById(id);
    }

    @PostMapping
    public RecipeIngredient createRecipeIngredient(@RequestBody RecipeIngredient recipeIngredient) {
        return recipeIngredientService.createRecipeIngredient(recipeIngredient);
    }

    @PutMapping("/{recipeId}/{ingredientId}")
    public RecipeIngredient updateRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, @RequestBody RecipeIngredient recipeIngredientDetails) {
        RecipeIngredientId id = new RecipeIngredientId(recipeId, ingredientId);
        return recipeIngredientService.updateRecipeIngredient(id, recipeIngredientDetails);
    }

    @DeleteMapping("/{recipeId}/{ingredientId}")
    public void deleteRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        RecipeIngredientId id = new RecipeIngredientId(recipeId, ingredientId);
        recipeIngredientService.deleteRecipeIngredient(id);
    }

}

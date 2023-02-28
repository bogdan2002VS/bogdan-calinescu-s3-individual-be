package com.veganny.recipeingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipeIngredients")
public class RecipeIngredientController {

    private RecipeIngredientService recipeIngredientService;

    @Autowired
    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

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

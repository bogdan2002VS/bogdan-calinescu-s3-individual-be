package com.veganny.controller;
import com.veganny.business.service.impl.RecipeService;
import com.veganny.configuration.security.isauthenticated.IsAuthenticated;
import com.veganny.domain.Recipe;
import com.veganny.persistence.entity.RecipeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{recipeId}")
    public Recipe getRecipeById(@PathVariable Long recipeId) {
        return recipeService.getRecipeById(recipeId);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping
    public RecipeEntity createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public RecipeEntity updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        return recipeService.updateRecipe(id, recipe);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "mealType", required = false) String mealType,
            @RequestParam(value = "caloriesFrom", required = false) Integer caloriesFrom,
            @RequestParam(value = "caloriesTo", required = false) Integer caloriesTo
    ) {
        List<Recipe> recipes = recipeService.searchRecipes(title, mealType, caloriesFrom, caloriesTo);
        return ResponseEntity.ok(recipes);
    }

}

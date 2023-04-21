package com.veganny.controller;

import com.veganny.business.service.impl.RecipeService;
import com.veganny.persistence.entity.RecipeEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    private RecipeService recipeService;

    @GetMapping
    public List<RecipeEntity> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeEntity getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping
    public RecipeEntity createRecipe(@RequestBody RecipeEntity recipeEntity) {
        return recipeService.createRecipe(recipeEntity);
    }

    @PutMapping("/{id}")
    public RecipeEntity updateRecipe(@PathVariable Long id, @RequestBody RecipeEntity recipeEntityDetails) {
        return recipeService.updateRecipe(id, recipeEntityDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
}

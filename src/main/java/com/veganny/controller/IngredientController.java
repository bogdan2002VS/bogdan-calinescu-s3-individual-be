package com.veganny.controller;

import com.veganny.persistence.entity.IngredientEntity;
import com.veganny.business.service.impl.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@AllArgsConstructor
public class IngredientController {

    private IngredientService ingredientService;

    @GetMapping
    public List<IngredientEntity> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public IngredientEntity getIngredientById(@PathVariable Long id) {
        return ingredientService.getIngredientById(id);
    }

    @PostMapping
    public IngredientEntity createIngredient(@RequestBody IngredientEntity ingredientEntity) {
        return ingredientService.createIngredient(ingredientEntity);
    }

    @PutMapping("/{id}")
    public IngredientEntity updateIngredient(@PathVariable Long id, @RequestBody IngredientEntity ingredientEntityDetails) {
        return ingredientService.updateIngredient(id, ingredientEntityDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
    }

}
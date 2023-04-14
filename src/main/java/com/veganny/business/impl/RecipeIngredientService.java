package com.veganny.business.impl;

import com.veganny.persistence.RecipeIngredientId;
import com.veganny.persistence.entity.RecipeIngredientEntity;
import com.veganny.persistence.RecipeIngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeIngredientService {


    private RecipeIngredientRepository recipeIngredientRepository;

    public List<RecipeIngredientEntity> getAllRecipeIngredients() {
        return recipeIngredientRepository.findAll();
    }

    public RecipeIngredientEntity getRecipeIngredientById(RecipeIngredientId id) {
        return recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RecipeIngredient not found with id: " + id));
    }

    public RecipeIngredientEntity createRecipeIngredient(RecipeIngredientEntity recipeIngredientEntity) {
        return recipeIngredientRepository.save(recipeIngredientEntity);
    }

    public RecipeIngredientEntity updateRecipeIngredient(RecipeIngredientId id, RecipeIngredientEntity recipeIngredientEntityDetails) {
        RecipeIngredientEntity recipeIngredientEntity = getRecipeIngredientById(id);
        recipeIngredientEntity.setQuantity(recipeIngredientEntityDetails.getQuantity());
        return recipeIngredientRepository.save(recipeIngredientEntity);
    }

    public void deleteRecipeIngredient(RecipeIngredientId id) {
        getRecipeIngredientById(id);
        recipeIngredientRepository.deleteById(id);
    }

}

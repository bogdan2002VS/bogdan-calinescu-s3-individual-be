package com.veganny.business;

import com.veganny.persistence.entity.Ingredient;
import com.veganny.persistence.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private IngredientRepository ingredientRepository;

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient was not found with id:" + id));
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(Long id, Ingredient ingredientDetails) {
        Ingredient ingredient = getIngredientById(id);
        ingredient.setName(ingredientDetails.getName());
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id) {
        getIngredientById(id);
        ingredientRepository.deleteById(id);
    }

}
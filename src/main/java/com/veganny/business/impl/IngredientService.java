package com.veganny.business.impl;

import com.veganny.persistence.entity.IngredientEntity;
import com.veganny.persistence.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IngredientService {

    private IngredientRepository ingredientRepository;

    public List<IngredientEntity> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public IngredientEntity getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient was not found with id:" + id));
    }

    public IngredientEntity createIngredient(IngredientEntity ingredientEntity) {
        return ingredientRepository.save(ingredientEntity);
    }

    public IngredientEntity updateIngredient(Long id, IngredientEntity ingredientEntityDetails) {
        IngredientEntity ingredientEntity = getIngredientById(id);
        ingredientEntity.setName(ingredientEntityDetails.getName());
        return ingredientRepository.save(ingredientEntity);
    }

    public void deleteIngredient(Long id) {
        getIngredientById(id);
        ingredientRepository.deleteById(id);
    }

}
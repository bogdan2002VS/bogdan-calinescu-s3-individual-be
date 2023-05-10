package com.veganny.business.service.impl;

import com.veganny.business.exception.NotFoundException;
import com.veganny.persistence.entity.RecipeEntity;
import com.veganny.persistence.JPARecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private JPARecipeRepository JPARecipeRepository;

    public List<RecipeEntity> getAllRecipes() {
        return JPARecipeRepository.findAll();
    }

    public RecipeEntity getRecipeById(Long id) {
        return JPARecipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe was not found with id:" + id));
    }

    public RecipeEntity createRecipe(RecipeEntity recipeEntity) {
        return JPARecipeRepository.save(recipeEntity);
    }

    public RecipeEntity updateRecipe(Long id, RecipeEntity recipeEntityDetails) {
        RecipeEntity recipeEntity = getRecipeById(id);

        recipeEntity.setName(recipeEntityDetails.getName());

        recipeEntity.setNutritionalScore(recipeEntityDetails.getNutritionalScore());
        recipeEntity.setDescription(recipeEntityDetails.getDescription());
        recipeEntity.setPrepTime(recipeEntityDetails.getPrepTime());
        recipeEntity.setCookTime(recipeEntityDetails.getCookTime());

        return JPARecipeRepository.save(recipeEntity);
    }

    public void deleteRecipe(Long id) {
        RecipeEntity recipeEntity = getRecipeById(id);
        JPARecipeRepository.delete(recipeEntity);
    }

}
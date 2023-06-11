package com.veganny.persistence;

import com.veganny.persistence.entity.RecipeEntity;

import java.util.List;

public interface RecipeRepositoryCustom {

    List<RecipeEntity> searchRecipes(String title, String mealType, Integer from, Integer to);
}

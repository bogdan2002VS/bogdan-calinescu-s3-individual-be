package com.veganny.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RecipeIngredient {

    private Recipe recipeEntity;

    private Ingredient ingredientEntity;

    private int quantity;
}

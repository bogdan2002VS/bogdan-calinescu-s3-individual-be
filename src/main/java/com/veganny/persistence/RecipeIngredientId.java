package com.veganny.persistence;

import com.veganny.persistence.entity.Ingredient;
import com.veganny.persistence.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RecipeIngredientId implements Serializable {

    private Long recipeId;
    private Long ingredientId;
}

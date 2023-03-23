package com.veganny.persistence;

import com.veganny.persistence.entity.Ingredient;
import com.veganny.persistence.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientId implements Serializable {

    private Long recipe;
    private Long ingredient;
}

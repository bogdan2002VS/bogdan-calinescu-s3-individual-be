package com.veganny.recipeingredient;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RecipeIngredientId implements Serializable {

    @Column(name = "RecipeID")
    private Long recipeId;

    @Column(name = "IngredientID")
    private Long ingredientId;

}
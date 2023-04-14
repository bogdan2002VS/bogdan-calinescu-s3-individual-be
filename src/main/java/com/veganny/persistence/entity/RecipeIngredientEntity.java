package com.veganny.persistence.entity;

import com.veganny.persistence.RecipeIngredientId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "RecipeIngredient")
@IdClass(RecipeIngredientId.class)
public class RecipeIngredientEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RecipeID", referencedColumnName = "ID")
    private RecipeEntity recipeEntity;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IngredientID", referencedColumnName = "ID")
    private IngredientEntity ingredientEntity;

    @Column(name = "Quantity")
    private int quantity;

}

package com.veganny.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veganny.persistence.RecipeIngredientId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "RecipeIngredient")
@IdClass(RecipeIngredientId.class)
public class RecipeIngredient {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RecipeID", referencedColumnName = "ID")
    private Recipe recipe;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IngredientID", referencedColumnName = "ID")
    private Ingredient ingredient;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "IngredientId", insertable = false, updatable = false)
    private Long ingredientId;
}
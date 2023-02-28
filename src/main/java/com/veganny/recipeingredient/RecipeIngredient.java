package com.veganny.recipeingredient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veganny.Ingredient.Ingredient;
import com.veganny.recipes.Recipe;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "RecipeIngredient")
public class RecipeIngredient {

    @EmbeddedId
    private RecipeIngredientId id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RecipeID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ID")
    @JoinColumn(name = "IngredientID", referencedColumnName = "ID")
    private Ingredient ingredient;
    @Column(name = "Quantity")
    private int quantity;

    public RecipeIngredientId getId() {
        return id;
    }

    public void setId(RecipeIngredientId id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

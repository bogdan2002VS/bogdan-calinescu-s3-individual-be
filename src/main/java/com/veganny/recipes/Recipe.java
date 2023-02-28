package com.veganny.recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veganny.category.Category;
import com.veganny.recipeingredient.RecipeIngredient;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",insertable=false, updatable=false)
    private Long id;

    @Column(name = "Name")
    private String name;
    @ManyToOne
    private  Category category;
    @Column(name = "Cuisine")
    private String cuisine;
    @Column(name = "NutritionalScore")
    private String nutritionalScore;
    @Column(name = "Description")
    private String description;
    @Column(name = "PrepTime")
    private int prepTime;
    @Column(name = "CookTime")
    private int cookTime;


}

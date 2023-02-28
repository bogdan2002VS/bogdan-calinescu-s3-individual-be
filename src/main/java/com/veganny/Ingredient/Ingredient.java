package com.veganny.Ingredient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veganny.recipeingredient.RecipeIngredient;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Name")
    private String name;



}

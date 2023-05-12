package com.veganny.persistence.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Recipe")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "Name")
    private String name;


    @Column(name = "NutritionalScore")
    private String nutritionalScore;

    @Column(name = "Description")
    private String description;

    @Column(name = "PrepTime")
    private String prepTime;

    @Column(name = "CookTime")
    private String cookTime;

}
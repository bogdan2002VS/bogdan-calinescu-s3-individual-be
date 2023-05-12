package com.veganny.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Recipe {

    private Long id;
    private String name;

    private Cuisine cuisineEntity;
    private String nutritionalScore;
    private String description;
    private String prepTime;
    private String cookTime;

}

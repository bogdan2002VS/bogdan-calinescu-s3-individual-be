package com.veganny.domain;


import com.veganny.persistence.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Recipe {

    private Long id;
    private String title;
    private Integer calories;
    private String mealType;
    private List<String> ingredients;
    private String image;
    private List<ReviewEntity> reviews;

}

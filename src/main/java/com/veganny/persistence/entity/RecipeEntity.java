package com.veganny.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import org.hibernate.search.annotations.Field;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Recipe")
@ToString
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Field
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String calories;

    @Field
    @Column(nullable = false)
    private String mealType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient")
    private List<String> ingredients = new ArrayList<>();


    private String image;




}

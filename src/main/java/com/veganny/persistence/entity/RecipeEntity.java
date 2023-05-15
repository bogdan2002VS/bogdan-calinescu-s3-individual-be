package com.veganny.persistence.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.repository.cdi.Eager;

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

    private String title;
    private String calories;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ingredients")
    @Column(name = "ingredient")
    private List<String> ingredients;

    private String image;

}
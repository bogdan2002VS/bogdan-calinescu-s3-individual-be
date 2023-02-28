package com.veganny.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veganny.recipes.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;

}

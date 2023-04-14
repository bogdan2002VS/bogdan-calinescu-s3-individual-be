package com.veganny.Populator;

import com.veganny.persistence.CategoryRepository;
import com.veganny.persistence.CuisineRepository;
import com.veganny.persistence.IngredientRepository;
import com.veganny.persistence.entity.Category;
import com.veganny.persistence.RecipeRepository;
import com.veganny.persistence.entity.Cuisine;
import com.veganny.persistence.entity.Ingredient;
import com.veganny.persistence.entity.Recipe;
import com.veganny.persistence.entity.RecipeIngredient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulator {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;
    private final CuisineRepository cuisineRepository;

    @Autowired
    public DatabasePopulator(RecipeRepository recipeRepository, CategoryRepository categoryRepository, IngredientRepository ingredientRepository, CuisineRepository cuisineRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
        this.cuisineRepository = cuisineRepository;
    }

    @PostConstruct
    public void populate() {
        // Populate categories
        // ...

        // Populate ingredients
        // ...

        // Populate cuisines
        Cuisine american = new Cuisine();
        american.setName("American");
        cuisineRepository.save(american);

        Cuisine italian = new Cuisine();
        italian.setName("Italian");
        cuisineRepository.save(italian);

        // Populate recipes
        Recipe recipe1 = new Recipe();
        recipe1.setName("Chocolate Cake");
        recipe1.setCategory(dessert);
        recipe1.setCuisine(american);
        // ...

        Recipe recipe2 = new Recipe();
        recipe2.setName("Pizza");
        recipe2.setCategory(mainDish);
        recipe2.setCuisine(italian);
        // ...
    }

    @PreDestroy
    public void cleanUp() {
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
        ingredientRepository.deleteAll();
        cuisineRepository.deleteAll();
    }
}
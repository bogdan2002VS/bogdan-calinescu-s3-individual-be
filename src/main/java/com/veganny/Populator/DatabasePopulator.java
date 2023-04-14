package com.veganny.Populator;

import com.veganny.persistence.CategoryRepository;
import com.veganny.persistence.CuisineRepository;
import com.veganny.persistence.IngredientRepository;
import com.veganny.persistence.entity.CategoryEntity;
import com.veganny.persistence.RecipeRepository;
import com.veganny.persistence.entity.CuisineEntity;
import com.veganny.persistence.entity.IngredientEntity;
import com.veganny.persistence.entity.RecipeEntity;
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
        CategoryEntity dessert = new CategoryEntity();
        dessert.setName("Dessert");
        dessert.setDescription("Yummie");
        categoryRepository.save(dessert);

        CategoryEntity mainDish = new CategoryEntity();
        mainDish.setName("Main Dish");
        mainDish.setDescription("Delicious");
        categoryRepository.save(mainDish);

        // Populate ingredients
        IngredientEntity flour = new IngredientEntity();
        flour.setName("Flour");
        ingredientRepository.save(flour);

        IngredientEntity sugar = new IngredientEntity();
        sugar.setName("Sugar");
        ingredientRepository.save(sugar);

        IngredientEntity salt = new IngredientEntity();
        salt.setName("Salt");
        ingredientRepository.save(salt);

        // Populate cuisines
        CuisineEntity american = new CuisineEntity();
        american.setName("American");
        cuisineRepository.save(american);

        CuisineEntity italian = new CuisineEntity();
        italian.setName("Italian");
        cuisineRepository.save(italian);

        // Populate recipes
        RecipeEntity recipeEntity1 = new RecipeEntity();
        recipeEntity1.setName("Chocolate Cake");
        recipeEntity1.setCategoryEntity(dessert);
        recipeEntity1.setCuisineEntity(american);
        recipeEntity1.setNutritionalScore("A");
        recipeEntity1.setDescription("The best chocolate cake ever.");
        recipeEntity1.setPrepTime("25 minutes");
        recipeEntity1.setCookTime("2 hour");
        recipeRepository.save(recipeEntity1);

        RecipeEntity recipeEntity2 = new RecipeEntity();
        recipeEntity2.setName("Pizza");
        recipeEntity2.setCategoryEntity(mainDish);
        recipeEntity2.setCuisineEntity(italian);
        recipeEntity2.setNutritionalScore("B");
        recipeEntity2.setDescription("Homemade pizza with fresh ingredients.");
        recipeEntity2.setPrepTime("30 minutes");
        recipeEntity2.setCookTime("15 minutes");
        recipeRepository.save(recipeEntity2);
    }

    @PreDestroy
    public void cleanUp() {
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
        ingredientRepository.deleteAll();
        cuisineRepository.deleteAll();
    }
}

package com.veganny.Populator;

import com.veganny.persistence.CategoryRepository;
import com.veganny.persistence.IngredientRepository;
import com.veganny.persistence.entity.Category;
import com.veganny.persistence.RecipeRepository;
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

        @Autowired
        public DatabasePopulator(RecipeRepository recipeRepository, CategoryRepository categoryRepository, IngredientRepository ingredientRepository) {
            this.recipeRepository = recipeRepository;
            this.categoryRepository = categoryRepository;
            this.ingredientRepository = ingredientRepository;
        }

        @PostConstruct
        public void populate() {
            // Populate categories
            Category dessert = new Category();
            dessert.setName("Dessert");
            dessert.setDescription("Yummie");
            categoryRepository.save(dessert);

            Category mainDish = new Category();
            mainDish.setName("Main Dish");
            mainDish.setDescription("Delicious");
            categoryRepository.save(mainDish);

            // Populate ingredients
            Ingredient flour = new Ingredient();
            flour.setName("Flour");
            ingredientRepository.save(flour);

            Ingredient sugar = new Ingredient();
            sugar.setName("Sugar");
            ingredientRepository.save(sugar);

            Ingredient salt = new Ingredient();
            salt.setName("Salt");
            ingredientRepository.save(salt);

            // Populate recipes
            Recipe recipe1 = new Recipe();
            recipe1.setName("Chocolate Cake");
            recipe1.setCategory(dessert);
            recipe1.setCuisine("Test");
            recipe1.setNutritionalScore("A");
            recipe1.setDescription("The best chocolate cake ever.");
            recipe1.setPrepTime("25 minutes");
            recipe1.setCookTime("2 hour");
            recipeRepository.save(recipe1);

            Recipe recipe2 = new Recipe();
            recipe2.setName("Pizza");
            recipe2.setCategory(mainDish);
            recipe2.setCuisine("Italian");
            recipe2.setNutritionalScore("B");
            recipe2.setDescription("Homemade pizza with fresh ingredients.");
            recipe2.setPrepTime("30 minutes");
            recipe2.setCookTime("15 minutes");
            recipeRepository.save(recipe2);
        }

        @PreDestroy
        public void cleanUp() {
            recipeRepository.deleteAll();
            categoryRepository.deleteAll();
            ingredientRepository.deleteAll();
        }
}
//    Recipe recipe = new Recipe();
////
//
//    Ingredient ingredient = new Ingredient();
////
//    RecipeIngredient recipeIngredient = new RecipeIngredient();
//recipeIngredient.setRecipe(recipe);
//        recipeIngredient.setIngredient(ingredient);
//        recipeIngredient.setQuantity(10);
//
//        recipeIngredientRepository.save(recipeIngredient);


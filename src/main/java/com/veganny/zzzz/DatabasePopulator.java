//package com.veganny.zzzz;
//
//import com.veganny.Ingredient.IngredientRepository;
//import com.veganny.category.Category;
//import com.veganny.category.CategoryRepository;
//import com.veganny.recipes.RecipeRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DatabasePopulator {
//
//    RecipeRepository recipeRepository;
//    CategoryRepository categoryRepository;
//    IngredientRepository ingredientRepository;
//
//    @Autowired
//    public DatabasePopulator(RecipeRepository recipeRepository, CategoryRepository categoryRepository, IngredientRepository ingredientRepository) {
//        this.recipeRepository = recipeRepository;
//        this.categoryRepository = categoryRepository;
//        this.ingredientRepository = ingredientRepository;
//    }
//
//    @PostConstruct
//    public void populate(){
//        Category category = new Category();
//        category.setDescription("ddd");
//        category.setName("Dessert");
//        categoryRepository.save(category);
//    }
//
//    @PreDestroy
//    public  void cleanUp(){
//        categoryRepository.deleteAll();
//    }
//}

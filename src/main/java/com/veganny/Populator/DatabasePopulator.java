package com.veganny.Populator;

import com.veganny.persistence.*;
import com.veganny.persistence.entity.*;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.security.SecureRandom;
import java.util.Arrays;

@Component
public class DatabasePopulator {

    private final RecipeRepository JPARecipeRepository;
    private final ReviewRepository reviewRepository;

    @Lazy
    private JPARoleRepository JPARoleRepository;
    @Lazy
    private JPAUserRepository JPAUserRepository;

    @Autowired
    public DatabasePopulator(RecipeRepository JPARecipeRepository, JPARoleRepository JPARoleRepository, JPAUserRepository JPAUserRepository, ReviewRepository reviewRepository) {
        this.JPARecipeRepository = JPARecipeRepository;
        this.JPARoleRepository = JPARoleRepository;
        this.JPAUserRepository = JPAUserRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostConstruct
    public void populate() {
        // Populate categories

        // Add a recipe
        RecipeEntity recipe = new RecipeEntity();
        recipe.setTitle("Spaghetti Bolognese");
        recipe.setCalories(500);
        recipe.setImage("spaghetti.jpg");
        recipe.setMealType("Lunch");
        recipe.setIngredients(Arrays.asList("spaghetti", "ground beef", "tomato sauce", "onion", "garlic"));

        JPARecipeRepository.save(recipe);

        //users
        RoleEntity userRole = RoleEntity.builder().id(1L).roleName("user").build();
        RoleEntity adminRole = RoleEntity.builder().id(2L).roleName("admin").build();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode("123");
        UserEntity admin = UserEntity.builder().role(adminRole).username("admin").password(encodedPassword).email("admin@example.com").firstName("Admin").lastName("User").address("Address").phone("+1234567890").build();
        UserEntity user = UserEntity.builder().role(userRole).username("user").password(encodedPassword).email("user@example.com").firstName("Regular").lastName("User").address("Address").phone("+1234567890").build();
        UserEntity bobo = UserEntity.builder().role(userRole).username("bobo").password(encodedPassword).email("bobo@example.com").firstName("Bobo").lastName("Doe").address("Address").phone("+1234567890").build();
        JPARoleRepository.save(userRole);
        JPARoleRepository.save(adminRole);
        JPAUserRepository.save(admin);
        JPAUserRepository.save(user);
        JPAUserRepository.save(bobo);

        // Add a review
        ReviewEntity review = new ReviewEntity();
        review.setStars(5);
        review.setRecipe(recipe);
        review.setUser(user);

        reviewRepository.save(review);
    }

    @PreDestroy
    public void cleanUp() {
        JPARecipeRepository.deleteAll();
    }
}

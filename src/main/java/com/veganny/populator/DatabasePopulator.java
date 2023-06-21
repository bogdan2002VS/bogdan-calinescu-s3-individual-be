package com.veganny.populator;

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

    private final RecipeRepository jpareciperepository;
    private final ReviewRepository reviewRepository;

    @Lazy
    private final JPARoleRepository jpaRoleRepository;
    @Lazy
    private final JPAUserRepository jpaUserRepository;

    @Autowired
    public DatabasePopulator(RecipeRepository jpareciperepository, JPARoleRepository jpaRoleRepository, JPAUserRepository jpaUserRepository, ReviewRepository reviewRepository) {
        this.jpareciperepository = jpareciperepository;
        this.jpaRoleRepository = jpaRoleRepository;
        this.jpaUserRepository = jpaUserRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostConstruct
    public void populate() {
        // Populate categories

        // Add a recipe
        RecipeEntity recipe = new RecipeEntity();
        recipe.setTitle("Spaghetti Bolognese");
        recipe.setCalories(500);
        recipe.setImage("");
        recipe.setMealType("Lunch");
        recipe.setIngredients(Arrays.asList("spaghetti", "ground beef", "tomato sauce", "onion", "garlic"));

        jpareciperepository.save(recipe);

        //users
        RoleEntity userRole = RoleEntity.builder().id(1L).roleName("user").build();
        RoleEntity adminRole = RoleEntity.builder().id(2L).roleName("admin").build();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String password = "123";
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        String phoneNumber =  "+1234567890";
        String adress = "Address";
        UserEntity admin = UserEntity.builder().role(adminRole).username("admin").password(encodedPassword).email("admin@example.com").firstName("Admin").lastName("User").address(adress).phone(phoneNumber).build();
        UserEntity user = UserEntity.builder().role(userRole).username("user").password(encodedPassword).email("user@example.com").firstName("Regular").lastName("User").address(adress).phone(phoneNumber).build();
        UserEntity bobo = UserEntity.builder().role(userRole).username("bobo").password(encodedPassword).email("bobo@example.com").firstName("Bobo").lastName("Doe").address(adress).phone(phoneNumber).build();
        jpaRoleRepository.save(userRole);
        jpaRoleRepository.save(adminRole);
        jpaUserRepository.save(admin);
        jpaUserRepository.save(user);
        jpaUserRepository.save(bobo);

        ReviewEntity review = new ReviewEntity();
        review.setStars(5);
        review.setRecipe(recipe);
        review.setUser(user);

        reviewRepository.save(review);
    }

    @PreDestroy
    public void cleanUp() {
        jpareciperepository.deleteAll();
    }
}

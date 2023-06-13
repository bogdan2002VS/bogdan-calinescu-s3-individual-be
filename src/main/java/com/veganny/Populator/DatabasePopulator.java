package com.veganny.Populator;

import com.veganny.persistence.*;
import com.veganny.persistence.CuisineRepository;
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

    private final CuisineRepository cuisineRepository;
    @Lazy
    private JPARoleRepository JPARoleRepository;
    @Lazy
    private JPAUserRepository JPAUserRepository;

    @Autowired
    public DatabasePopulator(RecipeRepository JPARecipeRepository, CuisineRepository cuisineRepository, JPARoleRepository JPARoleRepository, JPAUserRepository JPAUserRepository) {
        this.JPARecipeRepository = JPARecipeRepository;
        this.cuisineRepository = cuisineRepository;
        this.JPARoleRepository = JPARoleRepository;
        this.JPAUserRepository = JPAUserRepository;
    }

    @PostConstruct
    public void populate() {
        // Populate categories

        // Populate cuisines
        CuisineEntity american = new CuisineEntity();
        american.setName("American");
        cuisineRepository.save(american);

        CuisineEntity italian = new CuisineEntity();
        italian.setName("Italian");
        cuisineRepository.save(italian);

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
    }

    @PreDestroy
    public void cleanUp() {
        JPARecipeRepository.deleteAll();
        cuisineRepository.deleteAll();
    }
}

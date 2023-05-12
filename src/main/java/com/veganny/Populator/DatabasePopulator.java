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

@Component
public class DatabasePopulator {
    @Lazy

    private final JPARecipeRepository JPARecipeRepository;
    @Lazy

    private final IngredientRepository ingredientRepository;
    @Lazy

    private final CuisineRepository cuisineRepository;
    @Lazy

    private JPARoleRepository JPARoleRepository;
    @Lazy
    private JPAUserRepository JPAUserRepository;

    @Autowired
    public DatabasePopulator(JPARecipeRepository JPARecipeRepository, IngredientRepository ingredientRepository, CuisineRepository cuisineRepository, JPARoleRepository JPARoleRepository, JPAUserRepository JPAUserRepository) {
        this.JPARecipeRepository = JPARecipeRepository;
            this.ingredientRepository = ingredientRepository;
        this.cuisineRepository = cuisineRepository;
        this.JPARoleRepository = JPARoleRepository;
        this.JPAUserRepository = JPAUserRepository;
    }

    @PostConstruct
    public void populate() {
        // Populate categories


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


        recipeEntity1.setNutritionalScore("A");
        recipeEntity1.setDescription("The best chocolate cake ever.");
        recipeEntity1.setPrepTime("25 minutes");
        recipeEntity1.setCookTime("2 hour");
        JPARecipeRepository.save(recipeEntity1);

        RecipeEntity recipeEntity2 = new RecipeEntity();
        recipeEntity2.setName("Pizza");

        recipeEntity2.setNutritionalScore("B");
        recipeEntity2.setDescription("Homemade pizza with fresh ingredients.");
        recipeEntity2.setPrepTime("30 minutes");
        recipeEntity2.setCookTime("15 minutes");
        JPARecipeRepository.save(recipeEntity2);


        //users
        RoleEntity userRole = RoleEntity.builder().id(1L).roleName("user").build();
        RoleEntity adminRole = RoleEntity.builder().id(2L).roleName("admin").build();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode("123");
        UserEntity admin = UserEntity.builder().role(adminRole).username("admin").password(encodedPassword).email("email@email.com").firstName("ad").lastName("min").address("there").phone("+3111").build();
        UserEntity user = UserEntity.builder().role(userRole).username("test").password(encodedPassword).email("test@email.com").firstName("The").lastName("Tester").address("there").phone("+3111").build();
        UserEntity bobo = UserEntity.builder().role(userRole).username("bobo").password(encodedPassword).email("bobo@mail.com").firstName("bobo").lastName("Doe").address("there").phone("+3111").build();
        JPARoleRepository.save(userRole);
        JPARoleRepository.save(adminRole);
        JPAUserRepository.save(admin);
        JPAUserRepository.save(user);
        JPAUserRepository.save(bobo);
    }

    @PreDestroy
    public void cleanUp() {
        JPARecipeRepository.deleteAll();
        ingredientRepository.deleteAll();
        cuisineRepository.deleteAll();
    }
}

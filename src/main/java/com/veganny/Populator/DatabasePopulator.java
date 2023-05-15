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

    private final JPARecipeRepository JPARecipeRepository;


    //private final IngredientRepository ingredientRepository;


    private final CuisineRepository cuisineRepository;
    @Lazy

    private JPARoleRepository JPARoleRepository;
    @Lazy
    private JPAUserRepository JPAUserRepository;

    @Autowired
    public DatabasePopulator(JPARecipeRepository JPARecipeRepository, CuisineRepository cuisineRepository, JPARoleRepository JPARoleRepository, JPAUserRepository JPAUserRepository) {
        this.JPARecipeRepository = JPARecipeRepository;
          //  this.ingredientRepository = ingredientRepository;
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
        //ingredientRepository.deleteAll();
        cuisineRepository.deleteAll();
    }
}

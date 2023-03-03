package com.veganny.persistence;

import com.veganny.persistence.entity.RecipeIngredient;
import com.veganny.persistence.entity.RecipeIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {

}
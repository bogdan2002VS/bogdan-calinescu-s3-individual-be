package com.veganny.persistence;

import com.veganny.persistence.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPARecipeRepository extends JpaRepository<RecipeEntity, Long> {
}

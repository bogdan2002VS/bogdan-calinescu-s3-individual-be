package com.veganny.persistence;

import com.veganny.persistence.entity.RecipeEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    @EntityGraph(attributePaths = {"reviews", "ingredients"})
    @Query("SELECT r FROM RecipeEntity r WHERE r.id = :id")
    Optional<RecipeEntity> findByIdWithReviewsAndIngredients(@Param("id") Long id);
}

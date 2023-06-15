package com.veganny.persistence;

import com.veganny.persistence.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("SELECT r.stars, COUNT(r) AS reviewCount FROM ReviewEntity r WHERE r.recipe.id = :recipeId GROUP BY r.stars")
    List<Object[]> getReviewCountByStarRating(@Param("recipeId") Long recipeId);

    @Query("SELECT AVG(r.stars) FROM ReviewEntity r WHERE r.recipe.id = :recipeId")
    Double getAverageStarRating(@Param("recipeId") Long recipeId);

    ReviewEntity findByRecipeIdAndUserId(Long recipeId, Long userId);
}

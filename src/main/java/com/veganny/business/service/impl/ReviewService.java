package com.veganny.business.service.impl;

import com.veganny.domain.User;
import com.veganny.persistence.IUserRepository;
import com.veganny.persistence.RecipeRepository;
import com.veganny.persistence.ReviewRepository;
import com.veganny.persistence.entity.ReviewEntity;
import com.veganny.persistence.entity.converters.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final IUserRepository userRepository;

    private  final RecipeRepository recipeRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, IUserRepository userRepository, RecipeRepository recipeRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    public ReviewEntity saveReview(Integer rating, Long recipeId, Long userId) {
        User user = userRepository.findById(userId);

        ReviewEntity reviewEntity = reviewRepository.findByRecipeIdAndUserId(recipeId, userId);
        if(reviewEntity == null ){
            reviewEntity = new ReviewEntity();
            reviewEntity.setUser(UserConverter.convertToEntity(user));
            reviewEntity.setRecipe(recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + recipeId)));
        }
        reviewEntity.setStars(rating);

        return reviewRepository.save(reviewEntity);
    }



    public ReviewEntity getReviewByRecipeIdAndUserId(Long recipeId, Long userId) {
        return reviewRepository.findByRecipeIdAndUserId(recipeId, userId);
    }

    public List<Object[]> getReviewStatistics(Long recipeId) {
        return reviewRepository.getReviewCountByStarRating(recipeId);
    }


    public Double getAverageStarRating(Long recipeId) {
        return reviewRepository.getAverageStarRating(recipeId);
    }
}

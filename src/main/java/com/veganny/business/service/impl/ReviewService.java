package com.veganny.business.service.impl;

import com.veganny.domain.User;
import com.veganny.persistence.IUserRepository;
import com.veganny.persistence.RecipeRepository;
import com.veganny.persistence.ReviewRepository;
import com.veganny.persistence.entity.ReviewEntity;
import com.veganny.persistence.entity.UserEntity;
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
        this.recipeRepository =  recipeRepository;
    }

    public ReviewEntity saveReview(ReviewEntity review, Long recipeId, Long userId) {
        User user = userRepository.findById(userId);
        review.setUser(UserConverter.convertToEntity(user));
        review.setRecipe(recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + recipeId)));
        return reviewRepository.save(review);
    }

    public ReviewEntity getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with ID: " + id));
    }

    public List<Object[]> getReviewStatistics(Long recipeId) {
        return reviewRepository.getReviewCountByStarRating(recipeId);
    }

}

package com.veganny.business.service.impl;

import com.veganny.persistence.ReviewRepository;
import com.veganny.persistence.entity.ReviewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewEntity saveReview(ReviewEntity review) {
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

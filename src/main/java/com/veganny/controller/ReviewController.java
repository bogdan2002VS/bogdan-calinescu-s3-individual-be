package com.veganny.controller;

import com.veganny.business.service.impl.ReviewService;
import com.veganny.persistence.entity.RecipeEntity;
import com.veganny.persistence.entity.ReviewEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewEntity> createReview(@RequestBody ReviewEntity review, @RequestParam Long recipeId) {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(recipeId);
        review.setRecipe(recipe);
        ReviewEntity createdReview = reviewService.saveReview(review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewEntity> getReviewById(@PathVariable Long id) {
        ReviewEntity review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/statistics/{recipeId}")
    public ResponseEntity<Map<Integer, Long>> getReviewStatistics(@PathVariable Long recipeId) {
        List<Object[]> reviewStatistics = reviewService.getReviewStatistics(recipeId);

        // Prepare the result as a map of star rating to review count
        Map<Integer, Long> result = reviewStatistics.stream()
                .collect(Collectors.toMap(
                        arr -> (Integer) arr[0],
                        arr -> (Long) arr[1]
                ));
        System.out.println(result);
        return ResponseEntity.ok(result);
    }
}

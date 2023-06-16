package com.veganny.controller;

import com.veganny.business.jwt.AccessTokenHelper;
import com.veganny.business.service.impl.ReviewService;
import com.veganny.persistence.entity.ReviewEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final AccessTokenHelper accessTokenHelper;

    @Autowired
    public ReviewController(ReviewService reviewService, AccessTokenHelper accessTokenHelper) {
        this.reviewService = reviewService;
        this.accessTokenHelper = accessTokenHelper;
    }

    @PostMapping("/{id}")
    public ResponseEntity<ReviewEntity> saveReview(
            @PathVariable Long id,
            @RequestBody Integer rating,
            HttpServletRequest request
    ) {
        try {
            String accessToken = request.getHeader("Authorization").substring(7);
            Long userId = accessTokenHelper.decode(accessToken).getUserId();
            ReviewEntity savedReview = reviewService.saveReview(rating, id, userId);
            return ResponseEntity.ok(savedReview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{recipeId}")
    public ResponseEntity<ReviewEntity> getUserReview(
            @PathVariable Long recipeId,
            HttpServletRequest request
    ) {
        try {
            String accessToken = request.getHeader("Authorization").substring(7);
            Long userId = accessTokenHelper.decode(accessToken).getUserId();
            ReviewEntity review = reviewService.getReviewByRecipeIdAndUserId(recipeId, userId);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/statistics/{recipeId}")
    public ResponseEntity<Map<Integer, Long>> getReviewStatistics(@PathVariable Long recipeId) {
        List<Object[]> reviewStatistics = reviewService.getReviewStatistics(recipeId);
        Map<Integer, Long> result = reviewStatistics.stream()
                .collect(Collectors.toMap(
                        arr -> (Integer) arr[0],
                        arr -> (Long) arr[1]
                ));
        return ResponseEntity.ok(result);
    }



    @GetMapping("/average/{recipeId}")
    public ResponseEntity<Double> getAverageStarRating(@PathVariable Long recipeId) {
        Double averageStarRating = reviewService.getAverageStarRating(recipeId);
        if (averageStarRating == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(averageStarRating);
        }
    }

}

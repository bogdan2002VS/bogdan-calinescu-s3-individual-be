package com.veganny.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.veganny.business.service.impl.ReviewService;
import com.veganny.business.service.impl.UserService;
import com.veganny.domain.User;
import com.veganny.persistence.entity.RecipeEntity;
import com.veganny.persistence.entity.ReviewEntity;

import com.veganny.persistence.entity.UserEntity;
import com.veganny.persistence.entity.converters.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
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

    @PostMapping("/{id}")
    public ResponseEntity<ReviewEntity> saveReview(
            @PathVariable Long id,
            @RequestBody ReviewEntity review,
            HttpServletRequest request
    ) throws IOException {
        try {
            String accessToken = request.getHeader("Authorization").substring(7);
            String[] pieces = accessToken.split("\\.");
            String decoded = new String(Base64.getDecoder().decode(pieces[1]));
            JsonNode json = new ObjectMapper().readTree(decoded);
            Long userId = json.get("userId").asLong();
            ReviewEntity savedReview = reviewService.saveReview(review, id, userId);
            return ResponseEntity.ok(savedReview);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewEntity> getReviewById(@PathVariable Long id) {
        try {
            ReviewEntity review = reviewService.getReviewById(id);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/statistics/{recipeId}")
    public ResponseEntity<List<Object[]>> getReviewStatistics(@PathVariable Long recipeId) {
        try {
            List<Object[]> statistics = reviewService.getReviewStatistics(recipeId);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

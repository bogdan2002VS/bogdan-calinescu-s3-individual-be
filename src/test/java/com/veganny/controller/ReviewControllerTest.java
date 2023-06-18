package com.veganny.controller;

import com.veganny.business.jwt.AccessTokenHelper;
import com.veganny.business.service.impl.ReviewService;
import com.veganny.domain.AccessToken;
import com.veganny.persistence.entity.ReviewEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private AccessTokenHelper accessTokenHelper;

    @Mock
    private HttpServletRequest request;

    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewController = new ReviewController(reviewService, accessTokenHelper);
    }

    @Test
    void saveReview() {
        Long id = 1L;
        Integer rating = 5;
        String accessToken = "valid_access_token";
        Long userId = 123L;
        ReviewEntity savedReview = new ReviewEntity();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + accessToken);
        when(accessTokenHelper.decode(accessToken)).thenReturn(AccessToken.builder().userId(userId).build());
        when(reviewService.saveReview(rating, id, userId)).thenReturn(savedReview);

        ResponseEntity<ReviewEntity> result = reviewController.saveReview(id, rating, request);

        assertEquals(savedReview, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(reviewService, times(1)).saveReview(rating, id, userId);
        verify(request, times(1)).getHeader("Authorization");
        verify(accessTokenHelper, times(1)).decode(accessToken);
    }

    @Test
    void saveReviewWhenExceptionThrown() {
        Long id = 1L;
        Integer rating = 5;
        String accessToken = "valid_access_token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + accessToken);
        when(accessTokenHelper.decode(accessToken)).thenThrow(new RuntimeException());

        ResponseEntity<ReviewEntity> result = reviewController.saveReview(id, rating, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        verify(reviewService, never()).saveReview(any(), any(), any());
        verify(request, times(1)).getHeader("Authorization");
        verify(accessTokenHelper, times(1)).decode(accessToken);
    }

    @Test
    void getUserReview() {
        Long recipeId = 1L;
        String accessToken = "valid_access_token";
        Long userId = 123L;
        ReviewEntity review = new ReviewEntity();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + accessToken);
        when(accessTokenHelper.decode(accessToken)).thenReturn(AccessToken.builder().userId(userId).build());
        when(reviewService.getReviewByRecipeIdAndUserId(recipeId, userId)).thenReturn(review);

        ResponseEntity<ReviewEntity> result = reviewController.getUserReview(recipeId, request);

        assertEquals(review, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(reviewService, times(1)).getReviewByRecipeIdAndUserId(recipeId, userId);
        verify(request, times(1)).getHeader("Authorization");
        verify(accessTokenHelper, times(1)).decode(accessToken);
    }

    @Test
    void getUserReviewWhenExceptionThrown() {
        Long recipeId = 1L;
        String accessToken = "valid_access_token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + accessToken);
        when(accessTokenHelper.decode(accessToken)).thenThrow(new RuntimeException());

        ResponseEntity<ReviewEntity> result = reviewController.getUserReview(recipeId, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        verify(reviewService, never()).getReviewByRecipeIdAndUserId(any(), any());
        verify(request, times(1)).getHeader("Authorization");
        verify(accessTokenHelper, times(1)).decode(accessToken);
    }

    @Test
    void getReviewStatistics() {
        Long recipeId = 1L;
        List<Object[]> reviewStatistics = Arrays.asList(
                new Object[]{1, 5L},
                new Object[]{2, 3L}
        );
        Map<Integer, Long> expectedResult = new HashMap<>();
        expectedResult.put(1, 5L);
        expectedResult.put(2, 3L);

        when(reviewService.getReviewStatistics(recipeId)).thenReturn(reviewStatistics);

        ResponseEntity<Map<Integer, Long>> result = reviewController.getReviewStatistics(recipeId);

        assertEquals(expectedResult, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(reviewService, times(1)).getReviewStatistics(recipeId);
    }

    @Test
    void getAverageStarRating() {
        Long recipeId = 1L;
        Double averageStarRating = 4.5;

        when(reviewService.getAverageStarRating(recipeId)).thenReturn(averageStarRating);

        ResponseEntity<Double> result = reviewController.getAverageStarRating(recipeId);

        assertEquals(averageStarRating, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(reviewService, times(1)).getAverageStarRating(recipeId);
    }

    @Test
    void getAverageStarRatingWhenNoRatingFound() {
        Long recipeId = 1L;

        when(reviewService.getAverageStarRating(recipeId)).thenReturn(null);

        ResponseEntity<Double> result = reviewController.getAverageStarRating(recipeId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(reviewService, times(1)).getAverageStarRating(recipeId);
    }
}

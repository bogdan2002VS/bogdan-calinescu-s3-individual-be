package com.veganny.business.service.impl;

import com.veganny.domain.User;
import com.veganny.domain.impl.UserRole;
import com.veganny.persistence.IUserRepository;
import com.veganny.persistence.RecipeRepository;
import com.veganny.persistence.ReviewRepository;
import com.veganny.persistence.entity.RecipeEntity;
import com.veganny.persistence.entity.ReviewEntity;
import com.veganny.persistence.entity.converters.UserConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private ReviewService reviewService;


    @Test
    @DisplayName("Should return null when the recipeId and userId combination does not exist")
    void getReviewByRecipeIdAndUserIdWhenNotExist() {
        Long recipeId = 1L;
        Long userId = 2L;

        when(reviewRepository.findByRecipeIdAndUserId(recipeId, userId)).thenReturn(null);

        ReviewEntity result = reviewService.getReviewByRecipeIdAndUserId(recipeId, userId);

        assertNull(result);
        verify(reviewRepository, times(1)).findByRecipeIdAndUserId(recipeId, userId);
    }

    @Test
    @DisplayName("Should return the review when the recipeId and userId are valid")
    void getReviewByRecipeIdAndUserIdWhenValid() {
        Long recipeId = 1L;
        Long userId = 2L;
        User user = User.builder()
                .id(userId)
                .role(UserRole.builder()
                        .id(1L)
                        .role("USER")
                        .build())
                .username("testuser")
                .password("testpassword")
                .firstName("Test")
                .lastName("User")
                .email("testuser@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();

        // Assertion
        Assertions.assertEquals(userId, user.getId());
    }


    @Test
    @DisplayName("Should return null when no reviews are found for the given recipe ID")
    void getAverageStarRatingWhenNoReviewsFound() {
        Long recipeId = 1L;
        when(reviewRepository.getAverageStarRating(recipeId)).thenReturn(null);

        Double result = reviewService.getAverageStarRating(recipeId);

        assertNull(result);
        verify(reviewRepository, times(1)).getAverageStarRating(recipeId);
    }

    @Test
    @DisplayName("Should return the average star rating for the given recipe ID")
    void getAverageStarRating() {
        Long recipeId = 1L;
        Double expectedAverageRating = 4.5;


        when(reviewRepository.getAverageStarRating(recipeId)).thenReturn(expectedAverageRating);

        Double actualAverageRating = reviewService.getAverageStarRating(recipeId);

        verify(reviewRepository, times(1)).getAverageStarRating(recipeId);

        assertEquals(expectedAverageRating, actualAverageRating);
    }

    @Test
    @DisplayName("Should return review statistics for a given recipe ID")
    void getReviewStatisticsForGivenRecipeId() {
        Long recipeId = 1L;
        Long userId = 1L;

        User user = User.builder()
                .id(userId)
                .role(UserRole.builder()
                        .id(1L)
                        .role("USER")
                        .build())
                .username("john.doe")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();


        List<Object[]> reviewStatistics = reviewService.getReviewStatistics(recipeId);

        assertNotNull(reviewStatistics);
    }

    @Test
    @DisplayName("Should return an empty list when no reviews are found for a given recipe ID")
    void getReviewStatisticsWhenNoReviewsFound() {
        Long recipeId = 1L;
        when(reviewRepository.getReviewCountByStarRating(recipeId)).thenReturn(Arrays.asList());

        List<Object[]> reviewStatistics = reviewService.getReviewStatistics(recipeId);

        assertTrue(reviewStatistics.isEmpty());
        verify(reviewRepository, times(1)).getReviewCountByStarRating(recipeId);
    }

    @Test
    @DisplayName("Should return null when the recipeId and userId combination is not found")
    void getReviewByRecipeIdAndUserIdWhenNotFound() {
        Long recipeId = 1L;
        Long userId = 2L;

        when(reviewRepository.findByRecipeIdAndUserId(recipeId, userId)).thenReturn(null);

        ReviewEntity result = reviewService.getReviewByRecipeIdAndUserId(recipeId, userId);

        assertNull(result);
        verify(reviewRepository, times(1)).findByRecipeIdAndUserId(recipeId, userId);
    }

    @Test
    @DisplayName("Should save a new review when the user has not reviewed the recipe before")
    void saveReviewWhenUserHasNotReviewedRecipe() {
        Long userId = 1L;
        Long recipeId = 2L;
        Integer rating = 4;
        User user = User.builder()
                .id(userId)
                .role(new UserRole("user", 1L))
                .build();
        ReviewEntity existingReview = null;
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(recipeId);

        when(userRepository.findById(userId)).thenReturn(user);
        when(reviewRepository.findByRecipeIdAndUserId(recipeId, userId)).thenReturn(existingReview);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeEntity));
        when(reviewRepository.save(any(ReviewEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReviewEntity savedReview = reviewService.saveReview(rating, recipeId, userId);

        assertNotNull(savedReview);
        assertEquals(rating, savedReview.getStars());
        assertEquals(user, UserConverter.convertToPOJO(savedReview.getUser()));
        assertEquals(recipeEntity, savedReview.getRecipe());
        verify(userRepository, times(1)).findById(userId);
        verify(reviewRepository, times(1)).findByRecipeIdAndUserId(recipeId, userId);
        verify(recipeRepository, times(1)).findById(recipeId);
        verify(reviewRepository, times(1)).save(any(ReviewEntity.class));
    }

    @Test
    @DisplayName("Should update the existing review when the user has already reviewed the recipe")
    void saveReviewWhenUserHasAlreadyReviewedRecipe() {// create mock objects
        User user = User.builder()
                .id(1L)
                .role(new UserRole("user", 1L))
                .username("john.doe")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();

        ReviewEntity existingReview = new ReviewEntity();
        existingReview.setId(1L);
        existingReview.setStars(3);
        existingReview.setUser(UserConverter.convertToEntity(user));
        existingReview.setRecipe(new RecipeEntity(1L, "Test Recipe", 500, "Lunch", Arrays.asList("Ingredient 1", "Ingredient 2"), "test-image.jpg"));

        when(userRepository.findById(user.getId())).thenReturn(user);
        when(reviewRepository.findByRecipeIdAndUserId(existingReview.getRecipe().getId(), user.getId())).thenReturn(existingReview);
        when(reviewRepository.save(existingReview)).thenReturn(existingReview);

        ReviewEntity savedReview = reviewService.saveReview(4, existingReview.getRecipe().getId(), user.getId());

        assertEquals(existingReview.getId(), savedReview.getId());
        assertEquals(4, savedReview.getStars());
        assertEquals(existingReview.getUser(), savedReview.getUser());
        assertEquals(existingReview.getRecipe(), savedReview.getRecipe());

        verify(userRepository, times(1)).findById(user.getId());
        verify(reviewRepository, times(1)).findByRecipeIdAndUserId(existingReview.getRecipe().getId(), user.getId());
        verify(reviewRepository, times(1)).save(existingReview);
    }
}
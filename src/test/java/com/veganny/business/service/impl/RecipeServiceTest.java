package com.veganny.business.service.impl;

import com.veganny.business.exception.NotFoundException;
import com.veganny.domain.Recipe;
import com.veganny.persistence.RecipeRepository;
import com.veganny.persistence.entity.RecipeEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;


    @Test
    @DisplayName("Should convert a Recipe object to a RecipeEntity object")
    void convertToEntity() {
        Recipe recipe = Recipe.builder()
                .id(1L)
                .title("Test Recipe")
                .calories(500)
                .mealType("Dinner")
                .ingredients(List.of("Ingredient 1", "Ingredient 2"))
                .image("test-image.jpg")
                .build();

        RecipeEntity expectedRecipeEntity = new RecipeEntity();
        expectedRecipeEntity.setId(1L);
        expectedRecipeEntity.setTitle("Test Recipe");
        expectedRecipeEntity.setCalories(500);
        expectedRecipeEntity.setMealType("Dinner");
        expectedRecipeEntity.setIngredients(List.of("Ingredient 1", "Ingredient 2"));
        expectedRecipeEntity.setImage("test-image.jpg");

        RecipeEntity actualRecipeEntity = (RecipeEntity) ReflectionTestUtils.invokeMethod(recipeService, "convertToEntity", recipe);

        assertEquals(expectedRecipeEntity.getId(), actualRecipeEntity.getId());
        assertEquals(expectedRecipeEntity.getTitle(), actualRecipeEntity.getTitle());
        assertEquals(expectedRecipeEntity.getCalories(), actualRecipeEntity.getCalories());
        assertEquals(expectedRecipeEntity.getMealType(), actualRecipeEntity.getMealType());
        assertEquals(expectedRecipeEntity.getIngredients(), actualRecipeEntity.getIngredients());
        assertEquals(expectedRecipeEntity.getImage(), actualRecipeEntity.getImage());
    }

    @Test
    @DisplayName("Should convert a RecipeEntity to a Recipe DTO")
    void convertToDto() {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(1L);
        recipeEntity.setTitle("Test Recipe");
        recipeEntity.setCalories(500);
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Ingredient 1");
        ingredients.add("Ingredient 2");
        recipeEntity.setIngredients(ingredients);
        recipeEntity.setImage("test-image.jpg");
        recipeEntity.setMealType("Lunch");

        Recipe recipe = (Recipe) ReflectionTestUtils.invokeMethod(recipeService, "convertToDto", recipeEntity);

        assertEquals(recipeEntity.getId(), recipe.getId());
        assertEquals(recipeEntity.getTitle(), recipe.getTitle());
        assertEquals(recipeEntity.getCalories(), recipe.getCalories());
        assertEquals(recipeEntity.getIngredients(), recipe.getIngredients());
        assertEquals(recipeEntity.getImage(), recipe.getImage());
        assertEquals(recipeEntity.getMealType(), recipe.getMealType());
    }



    @Test
    @DisplayName("Should return all recipes when no search parameters are provided")
    void searchRecipesWhenNoParametersProvided() {
        List<RecipeEntity> recipeEntities = new ArrayList<>();
        recipeEntities.add(new RecipeEntity(1L, "Recipe 1", 500, "Lunch", List.of("Ingredient 1", "Ingredient 2"), "image1.jpg"));
        recipeEntities.add(new RecipeEntity(2L, "Recipe 2", 700, "Dinner", List.of("Ingredient 3", "Ingredient 4"), "image2.jpg"));
        when(recipeRepository.searchRecipes(null, null, null, null)).thenReturn(recipeEntities);

        List<Recipe> recipes = recipeService.searchRecipes(null, null, null, null);

        assertEquals(2, recipes.size());
        assertEquals("Recipe 1", recipes.get(0).getTitle());
        assertEquals("Lunch", recipes.get(0).getMealType());
        assertEquals(500, recipes.get(0).getCalories());
        assertEquals(List.of("Ingredient 1", "Ingredient 2"), recipes.get(0).getIngredients());
        assertEquals("image1.jpg", recipes.get(0).getImage());
        assertEquals("Recipe 2", recipes.get(1).getTitle());
        assertEquals("Dinner", recipes.get(1).getMealType());
        assertEquals(700, recipes.get(1).getCalories());
        assertEquals(List.of("Ingredient 3", "Ingredient 4"), recipes.get(1).getIngredients());
        assertEquals("image2.jpg", recipes.get(1).getImage());
    }

    @Test
    @DisplayName("Should return recipes with matching meal type when meal type query is provided")
    void searchRecipesWhenMealTypeQueryProvided() {
        String titleQuery = null;
        String mealTypeQuery = "breakfast";
        Integer caloriesFrom = null;
        Integer caloriesTo = null;

        List<RecipeEntity> recipeEntities = new ArrayList<>();
        recipeEntities.add(new RecipeEntity(1L, "Pancakes", 300, "breakfast", List.of("flour", "eggs", "milk"), "pancakes.jpg"));
        recipeEntities.add(new RecipeEntity(2L, "Omelette", 250, "breakfast", List.of("eggs", "cheese", "mushrooms"), "omelette.jpg"));

        when(recipeRepository.searchRecipes(titleQuery, mealTypeQuery, caloriesFrom, caloriesTo)).thenReturn(recipeEntities);

        List<Recipe> recipes = recipeService.searchRecipes(titleQuery, mealTypeQuery, caloriesFrom, caloriesTo);

        assertEquals(2, recipes.size());
        assertEquals("Pancakes", recipes.get(0).getTitle());
        assertEquals("Omelette", recipes.get(1).getTitle());
        assertEquals("breakfast", recipes.get(0).getMealType());
        assertEquals("breakfast", recipes.get(1).getMealType());
        assertEquals(300, recipes.get(0).getCalories());
        assertEquals(250, recipes.get(1).getCalories());
        assertEquals(List.of("flour", "eggs", "milk"), recipes.get(0).getIngredients());
        assertEquals(List.of("eggs", "cheese", "mushrooms"), recipes.get(1).getIngredients());
        assertEquals("pancakes.jpg", recipes.get(0).getImage());
        assertEquals("omelette.jpg", recipes.get(1).getImage());

        verify(recipeRepository, times(1)).searchRecipes(titleQuery, mealTypeQuery, caloriesFrom, caloriesTo);
    }

    @Test
    @DisplayName("Should return recipes matching all search parameters when multiple parameters are provided")
    void searchRecipesWhenMultipleParametersProvided() {
        String titleQuery = "Vegan Burger";
        String mealTypeQuery = "Lunch";
        Integer caloriesFrom = 500;
        Integer caloriesTo = 800;

        List<RecipeEntity> recipeEntities = new ArrayList<>();
        recipeEntities.add(new RecipeEntity(1L, "Vegan Burger", 600, "Lunch", List.of("Lentil Patty", "Avocado", "Tomato"), "burger.jpg"));
        recipeEntities.add(new RecipeEntity(2L, "Vegan Pizza", 700, "Dinner", List.of("Vegan Cheese", "Mushrooms", "Bell Peppers"), "pizza.jpg"));
        recipeEntities.add(new RecipeEntity(3L, "Vegan Curry", 800, "Dinner", List.of("Chickpeas", "Spinach", "Coconut Milk"), "curry.jpg"));

        when(recipeRepository.searchRecipes(titleQuery, mealTypeQuery, caloriesFrom, caloriesTo)).thenReturn(recipeEntities);

        List<Recipe> recipes = recipeService.searchRecipes(titleQuery, mealTypeQuery, caloriesFrom, caloriesTo);

        assertEquals(3, recipes.size());
        assertEquals("Vegan Burger", recipes.get(0).getTitle());
        assertEquals("Lunch", recipes.get(0).getMealType());
        assertEquals(600, recipes.get(0).getCalories());
        assertEquals(List.of("Lentil Patty", "Avocado", "Tomato"), recipes.get(0).getIngredients());
        assertEquals("burger.jpg", recipes.get(0).getImage());

        assertEquals("Vegan Pizza", recipes.get(1).getTitle());
        assertEquals("Dinner", recipes.get(1).getMealType());
        assertEquals(700, recipes.get(1).getCalories());
        assertEquals(List.of("Vegan Cheese", "Mushrooms", "Bell Peppers"), recipes.get(1).getIngredients());
        assertEquals("pizza.jpg", recipes.get(1).getImage());

        assertEquals("Vegan Curry", recipes.get(2).getTitle());
        assertEquals("Dinner", recipes.get(2).getMealType());
        assertEquals(800, recipes.get(2).getCalories());
        assertEquals(List.of("Chickpeas", "Spinach", "Coconut Milk"), recipes.get(2).getIngredients());
        assertEquals("curry.jpg", recipes.get(2).getImage());

        verify(recipeRepository, times(1)).searchRecipes(titleQuery, mealTypeQuery, caloriesFrom, caloriesTo);
    }

    @Test
    @DisplayName("Should return recipes with matching title when title query is provided")
    void searchRecipesWhenTitleQueryProvided() {
        String titleQuery = "Pasta";
        String mealTypeQuery = null;
        Integer caloriesFrom = null;
        Integer caloriesTo = null;

        List<RecipeEntity> recipeEntities = new ArrayList<>();
        RecipeEntity recipeEntity1 = new RecipeEntity();
        recipeEntity1.setId(1L);
        recipeEntity1.setTitle("Pasta with tomato sauce");
        recipeEntity1.setCalories(500);
        recipeEntity1.setMealType("Lunch");
        recipeEntity1.setIngredients(List.of("pasta", "tomato sauce"));
        recipeEntity1.setImage("https://example.com/pasta.jpg");
        recipeEntities.add(recipeEntity1);

        RecipeEntity recipeEntity2 = new RecipeEntity();
        recipeEntity2.setId(2L);
        recipeEntity2.setTitle("Pasta with pesto sauce");
        recipeEntity2.setCalories(600);
        recipeEntity2.setMealType("Dinner");
        recipeEntity2.setIngredients(List.of("pasta", "pesto sauce"));
        recipeEntity2.setImage("https://example.com/pesto.jpg");
        recipeEntities.add(recipeEntity2);

        when(recipeRepository.searchRecipes(titleQuery, mealTypeQuery, caloriesFrom, caloriesTo)).thenReturn(recipeEntities);

        List<Recipe> recipes = recipeService.searchRecipes(titleQuery, mealTypeQuery, caloriesFrom, caloriesTo);

        assertEquals(2, recipes.size());
        assertEquals("Pasta with tomato sauce", recipes.get(0).getTitle());
        assertEquals("Pasta with pesto sauce", recipes.get(1).getTitle());
        assertEquals(500, recipes.get(0).getCalories());
        assertEquals(600, recipes.get(1).getCalories());
        assertEquals("Lunch", recipes.get(0).getMealType());
        assertEquals("Dinner", recipes.get(1).getMealType());
        assertEquals(List.of("pasta", "tomato sauce"), recipes.get(0).getIngredients());
        assertEquals(List.of("pasta", "pesto sauce"), recipes.get(1).getIngredients());
        assertEquals("https://example.com/pasta.jpg", recipes.get(0).getImage());
        assertEquals("https://example.com/pesto.jpg", recipes.get(1).getImage());
    }

    @Test
    @DisplayName("Should throw NotFoundException when the id is not found")
    void deleteRecipeWhenIdIsNotFoundThenThrowNotFoundException() {
        Long id = 1L;
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeService.deleteRecipe(id));

        verify(recipeRepository, times(1)).findById(id);
        verify(recipeRepository, never()).delete(any(RecipeEntity.class));
    }

    @Test
    @DisplayName("Should delete the recipe when the id is valid")
    void deleteRecipeWhenIdIsValid() {
        Long id = 1L;
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(id);
        recipeEntity.setTitle("Test Recipe");
        recipeEntity.setCalories(500);
        recipeEntity.setMealType("Lunch");
        recipeEntity.setIngredients(new ArrayList<>());
        recipeEntity.setImage("test-image.jpg");

        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipeEntity));

        recipeService.deleteRecipe(id);

        verify(recipeRepository, times(1)).delete(recipeEntity);
    }

    @Test
    @DisplayName("Should throw NotFoundException when the recipe id is not found")
    void updateRecipeWhenIdNotFoundThenThrowNotFoundException() {
        Long id = 1L;
        Recipe recipe = Recipe.builder()
                .id(id)
                .title("Test Recipe")
                .calories(500)
                .mealType("Lunch")
                .ingredients(List.of("Ingredient 1", "Ingredient 2"))
                .image("test-image.jpg")
                .build();

        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeService.updateRecipe(id, recipe));

        verify(recipeRepository, times(1)).findById(id);
        verify(recipeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update the recipe with the given id and new data")
    void updateRecipeWithGivenIdAndNewData() {
        Recipe updatedRecipe = Recipe.builder()
                .id(1L)
                .title("Updated Recipe")
                .calories(500)
                .mealType("Dinner")
                .ingredients(List.of("Ingredient 1", "Ingredient 2"))
                .image("image.jpg")
                .build();

        RecipeEntity updatedRecipeEntity = new RecipeEntity();
        updatedRecipeEntity.setId(1L);
        updatedRecipeEntity.setTitle("Updated Recipe");
        updatedRecipeEntity.setCalories(500);
        updatedRecipeEntity.setMealType("Dinner");
        updatedRecipeEntity.setIngredients(List.of("Ingredient 1", "Ingredient 2"));
        updatedRecipeEntity.setImage("image.jpg");

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(updatedRecipeEntity));

        when(recipeRepository.save(any(RecipeEntity.class))).thenReturn(updatedRecipeEntity);

        RecipeEntity result = recipeService.updateRecipe(1L, updatedRecipe);

        verify(recipeRepository, times(1)).findById(1L);

        verify(recipeRepository, times(1)).save(updatedRecipeEntity);

        assertEquals(updatedRecipeEntity, result);
    }


    @Test
    @DisplayName("Should create and save a new recipe successfully")
    void createRecipeSuccessfully() {
        Recipe recipe = Recipe.builder()
                .title("Vegan Burger")
                .calories(500)
                .mealType("Lunch")
                .ingredients(List.of("Lentils", "Bread Crumbs", "Onion", "Garlic", "Tomato", "Lettuce"))
                .image("https://example.com/veganBurger.jpg")
                .build();

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setTitle("Vegan Burger");
        recipeEntity.setCalories(500);
        recipeEntity.setMealType("Lunch");
        recipeEntity.setIngredients(List.of("Lentils", "Bread Crumbs", "Onion", "Garlic", "Tomato", "Lettuce"));
        recipeEntity.setImage("https://example.com/veganBurger.jpg");

        when(recipeRepository.save(recipeEntity)).thenReturn(recipeEntity);

        RecipeEntity createdRecipeEntity = recipeService.createRecipe(recipe);

        verify(recipeRepository, times(1)).save(recipeEntity);

        assertEquals(recipeEntity, createdRecipeEntity);
    }

    @Test
    @DisplayName("Should throw NotFoundException when the id is not found")
    void getRecipeByIdWhenIdIsNotFoundThenThrowNotFoundException() {
        Long id = 1L;
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeService.getRecipeById(id));

        verify(recipeRepository, times(1)).findById(id);
        verifyNoMoreInteractions(recipeRepository);
    }

    @Test
    @DisplayName("Should return the recipe when the id is valid")
    void getRecipeByIdWhenIdIsValid() {
        Long id = 1L;
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(id);
        recipeEntity.setTitle("Test Recipe");
        recipeEntity.setCalories(500);
        recipeEntity.setMealType("Lunch");
        recipeEntity.setIngredients(new ArrayList<>());
        recipeEntity.setImage("test-image.jpg");

        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipeEntity));

        Recipe recipe = recipeService.getRecipeById(id);

        assertEquals(id, recipe.getId());
        assertEquals(recipeEntity.getTitle(), recipe.getTitle());
        assertEquals(recipeEntity.getCalories(), recipe.getCalories());
        assertEquals(recipeEntity.getMealType(), recipe.getMealType());
        assertEquals(recipeEntity.getIngredients(), recipe.getIngredients());
        assertEquals(recipeEntity.getImage(), recipe.getImage());

        verify(recipeRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return all recipes")
    void getAllRecipes() {
        List<RecipeEntity> recipeEntities = new ArrayList<>();
        RecipeEntity recipeEntity1 = new RecipeEntity();
        recipeEntity1.setId(1L);
        recipeEntity1.setTitle("Recipe 1");
        recipeEntity1.setCalories(500);
        recipeEntity1.setMealType("Lunch");
        recipeEntity1.setIngredients(List.of("Ingredient 1", "Ingredient 2"));
        recipeEntity1.setImage("image1.jpg");
        recipeEntities.add(recipeEntity1);

        RecipeEntity recipeEntity2 = new RecipeEntity();
        recipeEntity2.setId(2L);
        recipeEntity2.setTitle("Recipe 2");
        recipeEntity2.setCalories(700);
        recipeEntity2.setMealType("Dinner");
        recipeEntity2.setIngredients(List.of("Ingredient 3", "Ingredient 4"));
        recipeEntity2.setImage("image2.jpg");
        recipeEntities.add(recipeEntity2);

        when(recipeRepository.findAll()).thenReturn(recipeEntities);

        List<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(2, recipes.size());
        assertEquals(recipeEntity1.getId(), recipes.get(0).getId());
        assertEquals(recipeEntity1.getTitle(), recipes.get(0).getTitle());
        assertEquals(recipeEntity1.getCalories(), recipes.get(0).getCalories());
        assertEquals(recipeEntity1.getMealType(), recipes.get(0).getMealType());
        assertEquals(recipeEntity1.getIngredients(), recipes.get(0).getIngredients());
        assertEquals(recipeEntity1.getImage(), recipes.get(0).getImage());

        assertEquals(recipeEntity2.getId(), recipes.get(1).getId());
        assertEquals(recipeEntity2.getTitle(), recipes.get(1).getTitle());
        assertEquals(recipeEntity2.getCalories(), recipes.get(1).getCalories());
        assertEquals(recipeEntity2.getMealType(), recipes.get(1).getMealType());
        assertEquals(recipeEntity2.getIngredients(), recipes.get(1).getIngredients());
        assertEquals(recipeEntity2.getImage(), recipes.get(1).getImage());

        verify(recipeRepository, times(1)).findAll();
    }
}
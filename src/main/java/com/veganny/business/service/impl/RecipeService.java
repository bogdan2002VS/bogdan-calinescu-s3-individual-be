package com.veganny.business.service.impl;

import com.veganny.business.exception.NotFoundException;
import com.veganny.domain.Recipe;
import com.veganny.persistence.entity.RecipeEntity;
import com.veganny.persistence.RecipeRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {

    private RecipeRepository recipeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Recipe> getAllRecipes() {
        List<RecipeEntity> recipeEntities = recipeRepository.findAll();
        return recipeEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Recipe getRecipeById(Long id) {
        RecipeEntity recipeEntity = recipeRepository.findByIdWithReviewsAndIngredients(id)
                .orElseThrow(() -> new NotFoundException("Recipe was not found with id: " + id));
        return convertToDto(recipeEntity);
    }

    public RecipeEntity createRecipe(Recipe recipe) {
        RecipeEntity recipeEntity = convertToEntity(recipe);
        return recipeRepository.save(recipeEntity);
    }

    public RecipeEntity updateRecipe(Long id, Recipe recipe) {
        RecipeEntity existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe was not found with id: " + id));
        existingRecipe.setTitle(recipe.getTitle());
        existingRecipe.setCalories(recipe.getCalories());
        existingRecipe.setIngredients(recipe.getIngredients());
        existingRecipe.setImage(recipe.getImage());
        existingRecipe.setMealType(recipe.getMealType());
        return recipeRepository.save(existingRecipe);
    }

    public void deleteRecipe(Long id) {
        RecipeEntity recipeEntity = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe was not found with id: " + id));
        recipeRepository.delete(recipeEntity);
    }

    @Transactional(readOnly = true)
    public List<Recipe> searchRecipes(String titleQuery, String mealTypeQuery, Integer caloriesQuery) {
        try (FullTextSession fullTextSession = Search.getFullTextSession(entityManager.unwrap(Session.class))) {
            FullTextQuery fullTextQuery = createFullTextQuery(fullTextSession, titleQuery, mealTypeQuery, caloriesQuery);
            List<RecipeEntity> recipeEntities = fullTextQuery.list();

            return recipeEntities.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
    }

    private FullTextQuery createFullTextQuery(FullTextSession fullTextSession, String titleQuery, String mealTypeQuery, Integer caloriesQuery) {
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(RecipeEntity.class).get();

        BooleanJunction<?> booleanJunction = queryBuilder.bool();

        if (titleQuery != null) {
            booleanJunction.should(queryBuilder.keyword().onField("title").matching(titleQuery).createQuery());
        }

        if (mealTypeQuery != null) {
            booleanJunction.should(queryBuilder.keyword().onField("mealType").matching(mealTypeQuery).createQuery());
        }

        if (caloriesQuery != null) {
            booleanJunction.should(queryBuilder.range().onField("calories").from(0).to(caloriesQuery).createQuery());
        }

        return fullTextSession.createFullTextQuery(booleanJunction.createQuery(), RecipeEntity.class);
    }


    private Recipe convertToDto(RecipeEntity recipeEntity) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeEntity.getId());
        recipe.setTitle(recipeEntity.getTitle());
        recipe.setCalories(recipeEntity.getCalories());
        recipe.setIngredients(recipeEntity.getIngredients());
        recipe.setImage(recipeEntity.getImage());
        recipe.setMealType(recipeEntity.getMealType());
        return recipe;
    }

    private RecipeEntity convertToEntity(Recipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(recipe.getId());
        recipeEntity.setTitle(recipe.getTitle());
        recipeEntity.setCalories(recipe.getCalories());
        recipeEntity.setIngredients(recipe.getIngredients());
        recipeEntity.setImage(recipe.getImage());
        recipeEntity.setMealType(recipe.getMealType());
        return recipeEntity;
    }

}

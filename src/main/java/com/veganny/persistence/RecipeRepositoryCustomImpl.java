package com.veganny.persistence;

import com.veganny.persistence.entity.RecipeEntity;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RecipeEntity> searchRecipes(String title, String mealType, Integer from, Integer to) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RecipeEntity> query = criteriaBuilder.createQuery(RecipeEntity.class);
        Root<RecipeEntity> recipe = query.from(RecipeEntity.class);

        Path<String> titlePath = recipe.get("title");
        Path<String> mealTypePath = recipe.get("mealType");
        Path<Integer> caloriesPath = recipe.get("calories");

        List<Predicate> predicates = new ArrayList<>();

        if (Strings.isNotBlank(title)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(titlePath), "%" + title.toLowerCase() + "%"));
        }

        if (Strings.isNotBlank(mealType)) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(mealTypePath), mealType.toLowerCase()));
        }

        if (Objects.nonNull(from) && Objects.nonNull(to) ) {
            predicates.add(criteriaBuilder.between(caloriesPath, from, to));
        }

        query.select(recipe).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}

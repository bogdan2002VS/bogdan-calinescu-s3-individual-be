package com.veganny.persistence;

import com.veganny.persistence.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecipeRepository extends JpaRepository<RecipeEntity, Long>, RecipeRepositoryCustom {

}

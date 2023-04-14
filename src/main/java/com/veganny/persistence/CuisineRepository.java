package com.veganny.persistence;

import com.veganny.persistence.entity.CuisineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineRepository extends JpaRepository<CuisineEntity, Long> {
    // Add any custom query methods here
}
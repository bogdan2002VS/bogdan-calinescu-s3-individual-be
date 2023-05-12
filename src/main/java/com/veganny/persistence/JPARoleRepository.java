package com.veganny.persistence;
import com.veganny.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPARoleRepository extends JpaRepository<RoleEntity, Long> {

}

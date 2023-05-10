package com.veganny.persistence.impl;

import com.veganny.persistence.IRoleRepository;
import com.veganny.domain.IRole;
import com.veganny.domain.impl.AdminRole;
import com.veganny.domain.impl.UserRole;
import com.veganny.exception.ResourceNotFoundException;
import com.veganny.persistence.JPARoleRepository;
import com.veganny.persistence.entity.RoleEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleRepositoryImpl implements IRoleRepository {

    private final JPARoleRepository JPARoleRepository;
    @Lazy
    public RoleRepositoryImpl(JPARoleRepository JPARoleRepository) {
        this.JPARoleRepository = JPARoleRepository;
    }

    public IRole findById(Long id){
        Optional<RoleEntity> role = JPARoleRepository.findById(id);
        if(role.isEmpty()){
            throw new ResourceNotFoundException("Role", "id", id);
        }
        if(role.get().getRoleName().equals("user")){
            return new UserRole("user", role.get().getId());
        }
        else{
            return new AdminRole("admin", role.get().getId());
        }
    }
}
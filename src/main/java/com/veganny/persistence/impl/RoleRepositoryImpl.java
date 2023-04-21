package com.veganny.persistence.impl;

import com.veganny.persistence.IRoleRepository;
import com.veganny.domain.IRole;
import com.veganny.domain.impl.AdminRole;
import com.veganny.domain.impl.UserRole;
import com.veganny.exception.ResourceNotFoundException;
import com.veganny.persistence.RoleRepository;
import com.veganny.persistence.entity.RoleEntity;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleRepositoryImpl implements IRoleRepository {

    private final RoleRepository roleRepository;
    @Lazy
    public RoleRepositoryImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public IRole findById(Long id){
        Optional<RoleEntity> role = roleRepository.findById(id);
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
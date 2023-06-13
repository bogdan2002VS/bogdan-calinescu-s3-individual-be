package com.veganny.domain.impl;

import com.veganny.domain.IRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRole implements IRole {
    private String role;
    private Long id;

    public UserRole(String role, Long id){
        this.role = role;
        this.id = id;
    }

    public String getRole(){
        return role;
    }
    public Long getRoleId(){return id;}

}

package com.veganny.persistence;

import com.veganny.domain.IRole;

public interface IRoleRepository {
    IRole findById(Long id);
}

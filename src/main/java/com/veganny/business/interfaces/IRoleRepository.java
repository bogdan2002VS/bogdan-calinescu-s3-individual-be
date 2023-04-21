package com.veganny.business.interfaces;

import com.veganny.domain.IRole;

public interface IRoleRepository {
    IRole findById(Long id);
}

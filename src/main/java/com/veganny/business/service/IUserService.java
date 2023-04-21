package com.veganny.business.service;

import com.veganny.business.interfaces.IRoleRepository;
import com.veganny.business.interfaces.IUserRepository;
import com.veganny.business.jwt.IAccessTokenHelper;
import com.veganny.domain.User;
import com.veganny.domain.UserWithToken;

public interface IUserService {



    Long registerUser(User u);
    UserWithToken loginUser(String username, String password);
    public User getUserByUsername(String username);
    User getUserById(Long id);
    User getUserByAccessToken();
}

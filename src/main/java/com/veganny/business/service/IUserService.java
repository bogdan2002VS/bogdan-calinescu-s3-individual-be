package com.veganny.business.service;

import com.veganny.domain.User;
import com.veganny.domain.UserWithToken;

public interface IUserService {



    Long registerUser(User u);
    UserWithToken loginUser(String username, String password);

    User getUserById(Long id);

}

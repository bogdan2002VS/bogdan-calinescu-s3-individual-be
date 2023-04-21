package com.veganny.business.interfaces;


import com.veganny.domain.User;

public interface IUserRepository {
     User getUserByUsername(String username);
     User findById(Long id);
     Long saveUser(User user);
}

package com.veganny.persistence;


import com.veganny.domain.User;

public interface IUserRepository {
     User getUserByUsername(String username);
     User findById(Long id);
     Long saveUser(User user);
}

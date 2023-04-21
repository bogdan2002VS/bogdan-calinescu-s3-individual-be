package com.veganny.persistence.impl;

import com.veganny.business.interfaces.IUserRepository;
import com.veganny.business.service.IUserService;
import com.veganny.persistence.UserRepository;
import com.veganny.persistence.entity.converters.UserConverter;
import com.veganny.domain.User;
import com.veganny.exception.IncorrectCredentialsException;
import com.veganny.exception.ResourceNotFoundException;
import com.veganny.exception.UsernameExistsException;

import com.veganny.persistence.entity.UserEntity;

import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements IUserRepository {
    @Lazy
    private final UserRepository userRepository;
    @Lazy
    private final IUserService userService;

    @Override
    public Long saveUser(User user){
        UserEntity entity = UserConverter.convertToEntity(user);
        try{
            return userRepository.save(entity).getId();
        }
        catch (DataIntegrityViolationException ex){
            throw new UsernameExistsException();
        }

    }
    @Override
    public User findById(Long id){
        Optional<UserEntity> ue = userRepository.findById(id);
        if(ue.isEmpty()){
            throw new ResourceNotFoundException("User", "id", id);
        }
        return UserConverter.convertToPOJO(ue.get());
    }

    @Override
    public User getUserByUsername(String username){
        Optional<UserEntity> ue = userRepository.findByUsername(username);
        if(ue.isEmpty()){
            throw new IncorrectCredentialsException();
        }

        return UserConverter.convertToPOJO(ue.get());
    }

}

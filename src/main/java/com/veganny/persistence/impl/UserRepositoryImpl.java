package com.veganny.persistence.impl;

import com.veganny.persistence.IUserRepository;
import com.veganny.persistence.JPAUserRepository;
import com.veganny.persistence.entity.converters.UserConverter;
import com.veganny.domain.User;
import com.veganny.exception.IncorrectCredentialsException;
import com.veganny.exception.ResourceNotFoundException;
import com.veganny.exception.UsernameExistsException;

import com.veganny.persistence.entity.UserEntity;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements IUserRepository {
    private JPAUserRepository JPAUserRepository;
    @Lazy
    public UserRepositoryImpl(JPAUserRepository JPAUserRepository) {
        this.JPAUserRepository = JPAUserRepository;
    }

    @Override
    public Long saveUser(User user){
        UserEntity entity = UserConverter.convertToEntity(user);
        try{
            return JPAUserRepository.save(entity).getId();
        }
        catch (DataIntegrityViolationException ex){
            throw new UsernameExistsException();
        }

    }
    @Override
    public User findById(Long id){
        Optional<UserEntity> ue = JPAUserRepository.findById(id);
        if(ue.isEmpty()){
            throw new ResourceNotFoundException("User", "id", id);
        }
        return UserConverter.convertToPOJO(ue.get());
    }

    @Override
    public User getUserByUsername(String username){
        Optional<UserEntity> ue = JPAUserRepository.findByUsername(username);
        if(ue.isEmpty()){
            throw new IncorrectCredentialsException();
        }

        return UserConverter.convertToPOJO(ue.get());
    }

}

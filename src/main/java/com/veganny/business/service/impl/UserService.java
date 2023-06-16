package com.veganny.business.service.impl;
import com.veganny.persistence.IRoleRepository;
import com.veganny.persistence.IUserRepository;
import com.veganny.business.jwt.IAccessTokenHelper;
import com.veganny.business.service.IUserService;
import com.veganny.domain.IRole;
import com.veganny.domain.User;
import com.veganny.domain.UserWithToken;
import com.veganny.exception.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserService implements IUserService {

    private IUserRepository userRepository;


    private IAccessTokenHelper accessTokenHelper;


    private IRoleRepository roleRepository;



    @Autowired
    public UserService(IUserRepository userRepository, IAccessTokenHelper accessTokenHelper, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.accessTokenHelper = accessTokenHelper;
        this.roleRepository = roleRepository;
    }

    @Override
    public Long registerUser(User u){
        IRole role = roleRepository.findById(1L);
        u.setRole(role);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(u.getPassword());
        u.setPassword(encodedPassword);
        return userRepository.saveUser(u);
    }

    @Override
    public UserWithToken loginUser(String username, String password){
        User found = userRepository.getUserByUsername(username);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

        if(!bCryptPasswordEncoder.matches(password, found.getPassword())){
            throw new IncorrectCredentialsException();
        }
        String accesstoken = accessTokenHelper.generateAccessToken(found);

        return UserWithToken.builder().user(found).token(accesstoken).build();

    }

    @Override
    public User getUserById(Long id){
        return userRepository.findById(id);

    }

}

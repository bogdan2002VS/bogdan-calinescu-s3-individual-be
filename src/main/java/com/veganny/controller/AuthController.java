package com.veganny.controller;

import com.veganny.business.service.IUserService;
import com.veganny.controller.dto.UserDTO;
import com.veganny.controller.requests.CreateUserReq;
import com.veganny.controller.requests.LoginReq;
import com.veganny.controller.responses.GenericObjectResponse;
import com.veganny.controller.responses.LoginResponse;
import com.veganny.controller.responses.ResourceCreatedResponse;
import com.veganny.domain.User;
import com.veganny.domain.UserWithToken;
import com.veganny.persistence.entity.converters.UserConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private IUserService userService;

    @PostMapping("signup")
    public ResponseEntity<ResourceCreatedResponse> signUp(@RequestBody @Valid CreateUserReq req){
        User toCreate = User.builder().firstName(req.getFirstName()).lastName(req.getLastName()).email(req.getEmail()).username(req.getUsername()).password(req.getPassword()).address(req.getAddress()).phone(req.getPhone()).build();
        Long userId = userService.registerUser(toCreate);
        ResourceCreatedResponse res = ResourceCreatedResponse.builder().message("Register successful").id(userId).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("signin")
    public ResponseEntity<GenericObjectResponse> signIn(@RequestBody @Valid LoginReq req){
        UserWithToken uwt = userService.loginUser(req.getUsername(), req.getPassword());

        UserDTO userDTO = UserConverter.convertToDTO(uwt.getUser());
        LoginResponse loginres = LoginResponse.builder().user(userDTO).accessToken(uwt.getToken()).build();

        GenericObjectResponse res = GenericObjectResponse.builder().message("Login successful").obj(loginres).build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}

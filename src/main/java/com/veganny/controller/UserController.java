package com.veganny.controller;

import com.veganny.business.service.IUserService;
import com.veganny.controller.dto.UserDTO;
import com.veganny.controller.responses.GenericObjectResponse;
import com.veganny.domain.User;
import com.veganny.persistence.entity.converters.UserConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("{username}")
    public ResponseEntity<GenericObjectResponse> getUserProfile(@PathVariable String username){
        User loggedUser = userService.getUserByUsername(username);
        UserDTO userDTO = UserConverter.convertToDTO(loggedUser);
        GenericObjectResponse res = GenericObjectResponse.builder().message("User fetched").obj(userDTO).build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.status(HttpStatus.OK).body("UP");
    }
}

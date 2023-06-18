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
import com.veganny.exception.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController")
class AuthControllerTest {
    @Mock
    private IUserService userService;

    @InjectMocks
    private AuthController authController;


    @Test
    @DisplayName("Should throw an exception when the username is incorrect")
    void signInWhenUsernameIsIncorrectThenThrowException() {
        LoginReq loginReq = LoginReq.builder().username("incorrectUsername").password("password").build();
        when(userService.loginUser(any(), any())).thenThrow(new RuntimeException("Invalid username or password"));

        assertThrows(RuntimeException.class, () -> authController.signIn(loginReq));
        verify(userService, times(1)).loginUser(any(), any());
    }



    @Test
    @DisplayName("Should create a new user and return a successful response with user ID")
    void signUpWithValidData() {
        CreateUserReq createUserReq = CreateUserReq.builder()
                .username("testuser")
                .password("testpassword")
                .firstName("Test")
                .lastName("User")
                .email("testuser@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();

        User user = User.builder()
                .id(1L)
                .username(createUserReq.getUsername())
                .password(createUserReq.getPassword())
                .firstName(createUserReq.getFirstName())
                .lastName(createUserReq.getLastName())
                .email(createUserReq.getEmail())
                .address(createUserReq.getAddress())
                .phone(createUserReq.getPhone())
                .build();

        when(userService.registerUser(any(User.class))).thenReturn(user.getId());

        ResponseEntity<ResourceCreatedResponse> responseEntity = authController.signUp(createUserReq);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Register successful", responseEntity.getBody().getMessage());
        assertEquals(user.getId(), responseEntity.getBody().getId());

        verify(userService, times(1)).registerUser(any(User.class));
    }
}
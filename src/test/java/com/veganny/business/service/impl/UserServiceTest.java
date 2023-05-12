package com.veganny.business.service.impl;

import com.veganny.business.jwt.IAccessTokenHelper;
import com.veganny.domain.IRole;
import com.veganny.domain.User;
import com.veganny.domain.impl.UserRole;
import com.veganny.exception.IncorrectCredentialsException;
import com.veganny.persistence.IRoleRepository;
import com.veganny.persistence.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IAccessTokenHelper accessTokenHelper;

    @Mock
    private IRoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;



    @Test
    @DisplayName("Should get the user by their username")
    void getUserByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.getUserByUsername(username)).thenReturn(user);

        User result = userService.getUserByUsername(username);

        assertEquals(user, result);
        verify(userRepository, times(1)).getUserByUsername(username);
    }

    @Test
    @DisplayName("Should get the user by their ID")
    void getUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("testuser@example.com");
        user.setAddress("123 Main St");
        user.setPhone("555-555-5555");

        when(userRepository.findById(userId)).thenReturn(user);

        User result = userService.getUserById(userId);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should throw an exception when logging in with incorrect credentials")
    void loginUserWithIncorrectCredentialsThrowsException() {
        String username = "john.doe";
        String password = "password123";

        User user =
                User.builder()
                        .id(1L)
                        .username(username)
                        .password("$2a$10$JZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZ")
                        .build();

        when(userRepository.getUserByUsername(username)).thenReturn(user);

        assertThrows(
                IncorrectCredentialsException.class,
                () -> userService.loginUser(username, password));

        verify(userRepository, times(1)).getUserByUsername(username);
    }


    @Test
    @DisplayName("Should throw an exception when login with incorrect credentials")
    void loginUserWithIncorrectCredentialsThenThrowException() {
        String username = "john.doe";
        String password = "password123";

        User user =
                User.builder()
                        .id(1L)
                        .username(username)
                        .password("$2a$10$JZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZ")
                        .build();

        when(userRepository.getUserByUsername(username)).thenReturn(user);

        assertThrows(
                IncorrectCredentialsException.class,
                () -> userService.loginUser(username, password));

        verify(userRepository, times(1)).getUserByUsername(username);
    }
}
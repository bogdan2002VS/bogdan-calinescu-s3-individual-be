package com.veganny.business.service.impl;

import com.veganny.business.jwt.IAccessTokenHelper;
import com.veganny.domain.IRole;
import com.veganny.domain.User;
import com.veganny.domain.UserWithToken;
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
    @DisplayName("Should register a new user with a default role and encrypted password")
    void registerUserWithDefaultRoleAndEncryptedPassword() {
        User user = User.builder()
                .username("testuser")
                .password("testpassword")
                .firstName("Test")
                .lastName("User")
                .email("testuser@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();

        IRole role = UserRole.builder()
                .id(1L)
                .name("USER")
                .build();

        when(roleRepository.findById(1L)).thenReturn(role);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        when(userRepository.saveUser(userCaptor.capture())).thenReturn(1L);

        Long userId = userService.registerUser(user);

        assertEquals(1L, userId);
        User savedUser = userCaptor.getValue();
        assertEquals(role, savedUser.getRole());
        assertEquals(encodedPassword, savedUser.getPassword());
        verify(userRepository, times(1)).saveUser(user);
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw NullPointerException when the username is not found")
    void loginUserWhenUsernameNotFoundThenThrowException() {
        String username = "nonexistentuser";
        String password = "password";

        when(userRepository.getUserByUsername(username)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> userService.loginUser(username, password));

        verify(userRepository, times(1)).getUserByUsername(username);
        verifyNoMoreInteractions(userRepository, accessTokenHelper, roleRepository);
    }

    @Test
    @DisplayName("Should return UserWithToken when the username and password are correct")
    void loginUserWhenUsernameAndPasswordAreCorrect() {
        String username = "john.doe";
        String password = "password123";
        User user = User.builder()
                .id(1L)
                .role(new UserRole())
                .username(username)
                .password(new BCryptPasswordEncoder(10, new SecureRandom()).encode(password))
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();
        String accessToken = "abc123";
        UserWithToken expectedUserWithToken = UserWithToken.builder()
                .user(user)
                .token(accessToken)
                .build();

        when(userRepository.getUserByUsername(username)).thenReturn(user);
        when(accessTokenHelper.generateAccessToken(user)).thenReturn(accessToken);

        UserWithToken actualUserWithToken = userService.loginUser(username, password);

        assertEquals(expectedUserWithToken, actualUserWithToken);
        verify(userRepository, times(1)).getUserByUsername(username);
        verify(accessTokenHelper, times(1)).generateAccessToken(user);
    }

    @Test
    @DisplayName("Should throw IncorrectCredentialsException when the password is incorrect")
    void loginUserWhenPasswordIsIncorrectThenThrowException() {
        String username = "john.doe";
        String password = "password123";
        User user = User.builder()
                .id(1L)
                .username(username)
                .password("$2a$10$JZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZJZ")
                .build();
        when(userRepository.getUserByUsername(username)).thenReturn(user);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        when(bCryptPasswordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertThrows(IncorrectCredentialsException.class, () -> userService.loginUser(username, password));

        verify(userRepository, times(1)).getUserByUsername(username);
        verify(bCryptPasswordEncoder, times(1)).matches(password, user.getPassword());
        verifyNoMoreInteractions(userRepository, bCryptPasswordEncoder);
    }

    @Test
    @DisplayName("Should return null when the user id does not exist")
    void getUserByIdWhenUserIdDoesNotExist() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(null);

        User result = userService.getUserById(userId);

        verify(userRepository, times(1)).findById(userId);
        assertEquals(null, result);
    }

    @Test
    @DisplayName("Should return the user when the user id exists")
    void getUserByIdWhenUserIdExists() {
        Long userId = 1L;
        User user = new User(userId, new UserRole(), "testuser", "password", "Test", "User", "testuser@example.com", "123 Main St", "555-555-5555");
        when(userRepository.findById(userId)).thenReturn(user);

        User result = userService.getUserById(userId);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should register a new user with a hashed password and default role")
    void registerUserWithHashedPasswordAndDefaultRole() {
        User user = User.builder()
                .username("testuser")
                .password("testpassword")
                .firstName("Test")
                .lastName("User")
                .email("testuser@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();

        IRole role = new UserRole("USER", 1L);
        when(roleRepository.findById(1L)).thenReturn(role);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        when(userRepository.saveUser(userCaptor.capture())).thenReturn(1L);

        Long userId = userService.registerUser(user);

        User capturedUser = userCaptor.getValue();
        assertEquals(user.getUsername(), capturedUser.getUsername());
        assertEquals(encodedPassword, capturedUser.getPassword());
        assertEquals(role, capturedUser.getRole());
        assertEquals(userId, 1L);
    }

    @Test
    @DisplayName("Should return the saved user's ID after successful registration")
    void returnSavedUserIdAfterSuccessfulRegistration() {
        User user = User.builder()
                .id(1L)
                .username("john.doe")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();

        IRole role = new UserRole("USER", 1L);
        when(roleRepository.findById(1L)).thenReturn(role);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        when(userRepository.saveUser(user)).thenReturn(1L);

        Long savedUserId = userService.registerUser(user);

        verify(roleRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).saveUser(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals(user, capturedUser);
        assertEquals(1L, savedUserId);
    }
}
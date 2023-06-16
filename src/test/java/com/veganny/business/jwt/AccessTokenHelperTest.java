package com.veganny.business.jwt;

import com.veganny.domain.AccessToken;
import com.veganny.domain.IRole;
import com.veganny.domain.User;
import com.veganny.domain.impl.UserRole;
import com.veganny.exception.BadTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
 class AccessTokenHelperTest {

    private final IAccessTokenHelper accessTokenHelper = new AccessTokenHelper("E91E158E4C6656F68B1B5D1C316766DE98D2AD6EF3BFB44F78E9CFCDF5");

    @Test
    @DisplayName("Should throw a BadTokenException when decoding an invalid access token")
    void decodeInvalidAccessTokenThrowsBadTokenException() {
        String invalidAccessToken = "invalid_access_token";

        assertThrows(BadTokenException.class,
                () -> accessTokenHelper.decode(invalidAccessToken)
        );
    }

    @Test
    @DisplayName("Should generate a valid access token for a given user")
    void generateAccessTokenForUser() {
        IRole role = new UserRole("ADMIN", 1L);

        User user = User.builder()
                .id(1L)
                .role(role)
                .username("john.doe")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();

        String accessToken = accessTokenHelper.generateAccessToken(user);

        assertNotNull(accessToken);

        AccessToken decodedToken = accessTokenHelper.decode(accessToken);

        assertEquals(user.getUsername(), decodedToken.getUsername());
        assertEquals(user.getRole().getRole(), decodedToken.getRole());
        assertEquals(user.getId(), decodedToken.getUserId());
    }

    @Test
    @DisplayName("Should encode an access token with correct claims")
    void encodeAccessTokenWithClaims() {
        IRole role = new UserRole("ADMIN", 1L);

        User user = User.builder()
                .id(1L)
                .role(role)
                .username("john.doe")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();

        String accessTokenEncoded = accessTokenHelper.generateAccessToken(user);

        assertNotNull(accessTokenEncoded);

        AccessToken accessToken = accessTokenHelper.decode(accessTokenEncoded);

        assertEquals(user.getUsername(), accessToken.getUsername());
        assertEquals(user.getId(), accessToken.getUserId());
        assertEquals(user.getRole().getRole(), accessToken.getRole());
    }

    @Test
    @DisplayName("Should decode a valid access token and return the correct AccessToken object")
    void decodeValidAccessToken() {
        IRole role = new UserRole("ADMIN", 1L);
        User user = User.builder()
                .id(1L)
                .role(role)
                .username("testuser")
                .password("testpassword")
                .firstName("Test")
                .lastName("User")
                .email("testuser@example.com")
                .address("123 Main St")
                .phone("555-555-5555")
                .build();

        String accessTokenEncoded = accessTokenHelper.generateAccessToken(user);

        AccessToken accessToken = accessTokenHelper.decode(accessTokenEncoded);

        assertEquals(user.getUsername(), accessToken.getUsername());
        assertEquals(user.getRole().getRole(), accessToken.getRole());
        assertEquals(user.getId(), accessToken.getUserId());
    }

}
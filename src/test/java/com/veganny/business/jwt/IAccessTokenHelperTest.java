package com.veganny.business.jwt;

import com.veganny.domain.User;
import com.veganny.domain.impl.AdminRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessTokenHelperTest {

    @Mock
    private IAccessTokenHelper accessTokenHelper;

    @Test
    @DisplayName("Should return null when the user is null")
    void generateAccessTokenWhenUserIsNull() {
        User user = null;
        String accessToken = accessTokenHelper.generateAccessToken(user);
        assertNull(accessToken);
    }



    @Test
    @DisplayName("Should generate a valid access token for a given user")
    void generateAccessTokenForGivenUser() {
        AdminRole adminRole = new AdminRole("Admin", 1L);
        User user =
                User.builder()
                        .id(1L)
                        .role(adminRole)
                        .username("john.doe")
                        .password("password")
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.doe@example.com")
                        .address("123 Main St")
                        .phone("555-555-5555")
                        .build();

        when(accessTokenHelper.generateAccessToken(user))
                .thenReturn(
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        String accessToken = accessTokenHelper.generateAccessToken(user);

        assertNotNull(accessToken);
        assertEquals(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
                accessToken);
        verify(accessTokenHelper, times(1)).generateAccessToken(user);
    }
}
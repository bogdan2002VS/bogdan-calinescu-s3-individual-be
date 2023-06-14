package com.veganny.business.jwt;

import com.veganny.domain.AccessToken;
import com.veganny.domain.User;
import com.veganny.exception.BadTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class AccessTokenHelperTest {

    @Mock
    private User user;

    @Value("${jwt.secret}")
    private String secretKey;

    @InjectMocks
    private AccessTokenHelper accessTokenHelper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateAccessToken() {
        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("testuser");
        when(user.getRole()).thenReturn(null);

        String accessToken = accessTokenHelper.generateAccessToken(user);

        assertNotNull(accessToken);
        assertTrue(accessToken.length() > 0);
    }

    @Test
    public void testDecode() {
        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("testuser");
        when(user.getRole()).thenReturn(null);

        String accessToken = accessTokenHelper.generateAccessToken(user);

        AccessToken decodedToken = accessTokenHelper.decode(accessToken);

        assertNotNull(decodedToken);
        assertEquals(decodedToken.getUsername(), "testuser");
        assertNull(decodedToken.getRole());
        assertEquals(decodedToken.getUserId(), 1L);
    }

    @Test
    public void testDecodeWithInvalidToken() {
        assertThrows(BadTokenException.class, () -> accessTokenHelper.decode("invalid_token"));
    }
}
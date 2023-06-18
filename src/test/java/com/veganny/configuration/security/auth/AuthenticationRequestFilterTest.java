package com.veganny.configuration.security.auth;

import com.veganny.business.exception.InvalidAccessTokenException;
import com.veganny.business.jwt.IAccessTokenHelper;
import com.veganny.domain.AccessToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationRequestFilterTest {

    @Mock
    private IAccessTokenHelper accessTokenDecoder;

    @InjectMocks
    private AuthenticationRequestFilter authenticationRequestFilter;


    @Test
    @DisplayName("Should set up the Spring Security context with the correct authorities")
    void setupSpringSecurityContextWithCorrectAuthorities() {// Create a mock AccessToken object
        AccessToken accessToken = AccessToken.builder()
                .username("testUser")
                .role("admin")
                .userId(1L)
                .build();

        // Call the private method using ReflectionTestUtils
        ReflectionTestUtils.invokeMethod(authenticationRequestFilter, "setupSpringSecurityContext", accessToken);

        // Verify that the authentication token was set correctly
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getName(), "testUser");
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getAuthorities().size(), 1);
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString(), "ROLE_ADMIN");
    }

    @Test
    @DisplayName("Should set up the Spring Security context with the correct role and user details")
    void setupSpringSecurityContextWithCorrectRoleAndUserDetails() {// Create a mock AccessToken object
        AccessToken accessToken = AccessToken.builder()
                .username("testuser")
                .role("admin")
                .userId(1L)
                .build();

        // Call the private method using ReflectionTestUtils
        ReflectionTestUtils.invokeMethod(authenticationRequestFilter, "setupSpringSecurityContext", accessToken);

        // Verify that the authentication token was set correctly
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getName(), "testuser");
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getAuthorities().size(), 1);
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority(), "ROLE_ADMIN");
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getDetails(), accessToken);
    }






    @Test
    @DisplayName("Should set the response status to unauthorized and flush the buffer")
    void sendAuthenticationErrorSetsResponseStatusAndFlushesBuffer() {// Create mock objects for HttpServletRequest and HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Call the private method using ReflectionTestUtils
        ReflectionTestUtils.invokeMethod(authenticationRequestFilter, "sendAuthenticationError", response);

        // Verify that the response status is set to unauthorized and the buffer is flushed
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            verify(response, times(1)).flushBuffer();
        } catch (IOException e) {
            fail("IOException occurred while flushing buffer");
        }
    }

    @Test
    @DisplayName("Should send an authentication error when the access token is invalid")
    void doFilterInternalWhenAccessTokenIsInvalidThenSendAuthenticationError() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer invalid_token");

        InvalidAccessTokenException exception = new InvalidAccessTokenException("Invalid access token");
        when(accessTokenDecoder.decode("invalid_token")).thenThrow(exception);

        authenticationRequestFilter.doFilterInternal(request, response, chain);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response, times(1)).flushBuffer();
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    @DisplayName("Should continue the filter chain when the Authorization header is missing or does not start with Bearer")
    void doFilterInternalWhenAuthorizationHeaderIsMissingOrNotBearer() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        // Authorization header is missing
        when(request.getHeader("Authorization")).thenReturn(null);
        assertDoesNotThrow(() -> authenticationRequestFilter.doFilterInternal(request, response, chain));
        verify(chain, times(1)).doFilter(request, response);

        // Authorization header does not start with Bearer
        when(request.getHeader("Authorization")).thenReturn("Basic abc123");
        assertDoesNotThrow(() -> authenticationRequestFilter.doFilterInternal(request, response, chain));
        verify(chain, times(2)).doFilter(request, response);
    }

    @Test
    @DisplayName("Should continue the filter chain and set up the Spring Security context when the access token is valid")
    void doFilterInternalWhenAccessTokenIsValid() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        String accessToken = "valid_access_token";
        AccessToken accessTokenDTO = AccessToken.builder()
                .username("test_user")
                .role("admin")
                .userId(1L)
                .build();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + accessToken);
        when(accessTokenDecoder.decode(accessToken)).thenReturn(accessTokenDTO);

        assertDoesNotThrow(() -> authenticationRequestFilter.doFilterInternal(request, response, chain));

        verify(chain, times(1)).doFilter(request, response);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(accessTokenDTO, SecurityContextHolder.getContext().getAuthentication().getDetails());
    }
}
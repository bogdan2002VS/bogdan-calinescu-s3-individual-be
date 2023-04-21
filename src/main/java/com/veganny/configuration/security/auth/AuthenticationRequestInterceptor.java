package com.veganny.configuration.security.auth;

import com.veganny.business.jwt.IAccessTokenHelper;
import com.veganny.domain.AccessToken;
import com.veganny.exception.BadTokenException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//It will look for a JWT token inside an authentication bearer HTTP header.
// If one is found then it will be verified, decoded and the authenticated user will be configured into Spring Security context.
@Component
public class AuthenticationRequestInterceptor extends OncePerRequestFilter {
    private static final String SPRING_SECURITY_ROLE_PREFIX = "ROLE_";

    @Autowired
    private IAccessTokenHelper accessTokenHelper;

    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {


        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String reqToken = requestTokenHeader.substring(7);

        try {
            AccessToken accessToken = accessTokenHelper.decode(reqToken);
            setupSpringSecurityContext(accessToken);
            filterChain.doFilter(request, response);
        } catch (BadTokenException e) {
            logger.error("Error validating access token", e);
            sendAuthenticationError(response, e);
        }
    }

    private void sendAuthenticationError(jakarta.servlet.http.HttpServletResponse response, BadTokenException e) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        response.flushBuffer();
    }

    private void setupSpringSecurityContext(AccessToken accessToken) {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(SPRING_SECURITY_ROLE_PREFIX + accessToken.getRole().toUpperCase()));
        UserDetails userDetails = new User(accessToken.getUsername(), "",
                roles);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(accessToken);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }


}

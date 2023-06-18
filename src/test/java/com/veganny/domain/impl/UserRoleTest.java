package com.veganny.domain.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UserRole class tests")
class UserRoleTest {

    @Test
    @DisplayName("Should return the correct role when getRole is called")
    void getRoleReturnsCorrectRole() {
        UserRole userRole = new UserRole("admin", 1L);
        assertEquals("admin", userRole.getRole());
    }

    @Test
    @DisplayName("Should return the correct role ID when getRoleId is called")
    void getRoleIdReturnsCorrectRoleId() {
        UserRole userRole = UserRole.builder()
                .role("admin")
                .id(1L)
                .build();

        assertEquals(1L, userRole.getRoleId());
    }

}
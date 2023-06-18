package com.veganny.domain.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("AdminRole class test")
class AdminRoleTest {

    @Test
    @DisplayName("Should return the role of the AdminRole object")
    void getRole() {
        AdminRole adminRole = new AdminRole("admin", 1L);
        assertEquals("admin", adminRole.getRole());
    }

    @Test
    @DisplayName("Should return the id of the AdminRole object")
    void getRoleId() {
        AdminRole adminRole = AdminRole.builder().role("admin").id(1L).build();
        assertEquals(1L, adminRole.getRoleId());
    }

    @Test
    @DisplayName("Should create an AdminRole object with the given role and id")
    void adminRoleConstructor() {
        String role = "admin";
        Long id = 1L;
        AdminRole adminRole = new AdminRole(role, id);

        assertEquals(role, adminRole.getRole());
        assertEquals(id, adminRole.getRoleId());
    }

}
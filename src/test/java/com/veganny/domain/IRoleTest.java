package com.veganny.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IRoleTest {

    @Mock
    IRole role;

    @Test
    @DisplayName("Should return the correct role when called")
    void getRoleReturnsCorrectRole() {
        when(role.getRole()).thenReturn("Admin");
        assertEquals("Admin", role.getRole());
        verify(role, times(1)).getRole();
    }

}
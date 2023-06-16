package com.veganny.business.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class NotFoundExceptionTest {

    @Test
    @DisplayName("Should create a NotFoundException with the given message")
    void createNotFoundExceptionWithMessage() {
        String message = "Resource not found";
        NotFoundException notFoundException = new NotFoundException(message);

        assertNotNull(notFoundException);
        assertEquals(message, notFoundException.getMessage());
    }

}
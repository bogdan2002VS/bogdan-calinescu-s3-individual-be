package com.veganny.business.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class InvalidAccessTokenExceptionTest {

    @Test
    @DisplayName("Should return the correct HttpStatus when calling getStatus")
    void getStatusReturnsUnauthorized() {
        InvalidAccessTokenException exception = new InvalidAccessTokenException("Invalid access token");
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }

    @Test
    @DisplayName("Should return the correct error cause when calling getReason")
    void getReasonReturnsErrorCause() {
        InvalidAccessTokenException invalidAccessTokenException = new InvalidAccessTokenException("Invalid access token");
        assertEquals("Invalid access token", invalidAccessTokenException.getReason());
    }

    @Test
    @DisplayName("Should create an instance of InvalidAccessTokenException with the given error cause")
    void invalidAccessTokenExceptionConstructor() {
        String errorCause = "Invalid access token";
        InvalidAccessTokenException exception = new InvalidAccessTokenException(errorCause);

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals(errorCause, exception.getReason());
    }

}
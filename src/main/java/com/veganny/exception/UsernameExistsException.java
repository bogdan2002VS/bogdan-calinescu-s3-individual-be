package com.veganny.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameExistsException extends ResponseStatusException {
    public UsernameExistsException(){
        super(HttpStatus.CONFLICT, "Username is taken");
    }
}

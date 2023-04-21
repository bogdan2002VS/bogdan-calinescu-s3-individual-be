package com.veganny.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String resourceName) {
        super(String.format("You are not authorized to modify resource:%s", resourceName));

    }


}
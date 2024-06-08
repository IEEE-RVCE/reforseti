package org.ieeervce.api.siterearnouveau.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LoginFailedException extends Exception {
    public LoginFailedException(String message) {
        super(message);
    }
}

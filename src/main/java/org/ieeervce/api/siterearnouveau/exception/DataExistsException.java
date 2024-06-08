package org.ieeervce.api.siterearnouveau.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataExistsException extends Exception {
    public DataExistsException(String message) {
        super(message);
    }
}

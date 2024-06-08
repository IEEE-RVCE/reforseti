package org.ieeervce.api.siterearnouveau.controller.error;

import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.exception.DataNotFoundException;
import org.ieeervce.api.siterearnouveau.exception.LoginFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);


    @ExceptionHandler({LoginFailedException.class, DataNotFoundException.class})
    public ResponseEntity<ResultsDTO<Void>> loginErrorHandler(Exception exception) {
        HttpStatus status = getStatusFromClassAnnotation(exception);
        return ResponseEntity
                .status(status.value())
                .body(
                        new ResultsDTO<>(false, null, exception.getLocalizedMessage())
                );
    }

    private HttpStatus getStatusFromClassAnnotation(Exception exception) {
        return Arrays.stream(exception.getClass().getAnnotationsByType(ResponseStatus.class))
                .findFirst()
                .map(ResponseStatus::value)
                .orElseGet(this::getDefaultExceptionStatus);
    }

    private HttpStatus getDefaultExceptionStatus() {
        LOGGER.error("Failed to get exception status. Using fallback response status");
        return HttpStatus.I_AM_A_TEAPOT;
    }

}

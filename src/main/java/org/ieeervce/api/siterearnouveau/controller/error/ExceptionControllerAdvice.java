package org.ieeervce.api.siterearnouveau.controller.error;

import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.exception.DataExistsException;
import org.ieeervce.api.siterearnouveau.exception.DataNotFoundException;
import org.ieeervce.api.siterearnouveau.exception.LoginFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
    static final String BAD_INPUT = "Bad Input";


    @ExceptionHandler({LoginFailedException.class, DataNotFoundException.class, DataExistsException.class})
    public ResponseEntity<ResultsDTO<Void>> loginErrorHandler(Exception exception) {
        HttpStatus status = getStatusFromClassAnnotation(exception);
        return ResponseEntity
                .status(status.value())
                .body(
                        new ResultsDTO<>(false, null, exception.getLocalizedMessage())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultsDTO<Map<String,String>>> formatErrorHandler(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResultsDTO<>(false,errors, BAD_INPUT)) ;
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

package com.max.api.v1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RangeExceptionHandler {

    @ExceptionHandler({InvalidRangeException.class, RangeTypeMismatchException.class})
    public ResponseEntity<RangeExceptionResponse> handleInvalidRangeException(RuntimeException e) {
        RangeExceptionResponse response = new RangeExceptionResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

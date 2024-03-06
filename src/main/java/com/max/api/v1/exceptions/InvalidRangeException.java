package com.max.api.v1.exceptions;

public class InvalidRangeException extends RuntimeException {
    public InvalidRangeException() {
        super();
    }

    public InvalidRangeException(String message) {
        super(message);
    }
}

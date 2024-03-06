package com.max.api.v1.exceptions;

public class RangeTypeMismatchException extends RuntimeException {
    public RangeTypeMismatchException() {
        super();
    }

    public RangeTypeMismatchException(String message) {
        super(message);
    }
}

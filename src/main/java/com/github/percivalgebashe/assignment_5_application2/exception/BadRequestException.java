package com.github.percivalgebashe.assignment_5_application2.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

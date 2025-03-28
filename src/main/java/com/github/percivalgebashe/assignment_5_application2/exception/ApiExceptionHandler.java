package com.github.percivalgebashe.assignment_5_application2.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFound(ResourceNotFoundException ex) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                "Resource Not Found");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return "Custom 404 Message: Endpoint not found!";
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage badRequest(BadRequestException ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                "Bad Request");
    }

    @ExceptionHandler(value = NoContentFoundException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ErrorMessage noContentFound(NoContentFoundException ex) {
        return new ErrorMessage(HttpStatus.NO_CONTENT.value(),
                new Date(),
                ex.getMessage(),
                "No Content Found");
    }

    @ExceptionHandler(value = ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage conflict(ConflictException ex) {
        return new ErrorMessage(HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                "Conflict! Resource Already Exists");
    }
}

package com.att.tdp.bisbis10.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Advice class to handle exceptions related to dish not found.
 */
@ControllerAdvice
public class DishNotFoundAdvice {

    /**
     * Handles the DishNotFoundException by returning a response body with the exception message
     * and setting the HTTP status code to 404 (NOT_FOUND).
     *
     * @param ex the DishNotFoundException to handle
     * @return a response body containing the exception message
     */
    @ResponseBody
    @ExceptionHandler(DishNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    final String dishNotFoundHandler(final DishNotFoundException ex) {
        return ex.getMessage();
    }
}

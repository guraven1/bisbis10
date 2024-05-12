package com.att.tdp.bisbis10.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Advice to handle exceptions of type {@link RestaurantNotFoundException}.
 * This class annotates a method to handle exceptions of {@link RestaurantNotFoundException}
 * and returns a custom message with HTTP status code 404 (NOT_FOUND).
 */
@ControllerAdvice
public class RestaurantNotFoundAdvice {

  /**
   * Handles RestaurantNotFoundException and returns
   * a custom message with HTTP status code 404 (NOT_FOUND).
   *
   * @param ex the exception instance of {@link RestaurantNotFoundException} to handle
   * @return a custom error message indicating the restaurant was not found
   */
  @ResponseBody
  @ExceptionHandler(RestaurantNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  final String restaurantNotFoundHandler(final RestaurantNotFoundException ex) {
    return ex.getMessage();
  }
}

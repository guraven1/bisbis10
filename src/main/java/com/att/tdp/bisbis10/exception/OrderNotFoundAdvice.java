package com.att.tdp.bisbis10.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller advice to handle exceptions of type {@link OrderNotFoundException}.
 */
@ControllerAdvice
public class OrderNotFoundAdvice {

  /**
   * Handles the {@link OrderNotFoundException} exception.
   *
   * @param ex The exception instance.
   * @return The error message provided by the exception.
   */
  @ResponseBody
  @ExceptionHandler(OrderNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  final String orderNotFoundHandler(final OrderNotFoundException ex) {
    return ex.getMessage();
  }
}

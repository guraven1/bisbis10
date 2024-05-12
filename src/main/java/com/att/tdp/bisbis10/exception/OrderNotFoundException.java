package com.att.tdp.bisbis10.exception;

/**
 * Exception thrown when an order is not found.
 */
public class OrderNotFoundException extends RuntimeException {

  /**
   * Constructs a new OrderNotFoundException with the specified ID.
   *
   * @param id the ID of the order that was not found
   */
  public OrderNotFoundException(final String id) {
    super("Order not found with ID: " + id);
  }
}

package com.att.tdp.bisbis10.exception;

/**
 * Exception thrown when a restaurant with a specific ID is not found.
 */
public class RestaurantNotFoundException extends RuntimeException {

  /**
   * Constructs a new RestaurantNotFoundException with the specified ID.
   *
   * @param id the ID of the restaurant that was not found
   */
  public RestaurantNotFoundException(final Long id) {
    super("Restaurant not found with ID: " + id);
  }
}

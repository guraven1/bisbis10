package com.att.tdp.bisbis10.exception;

/**
 * Exception indicating that a dish with a specific ID was not found.
 */
public class DishNotFoundException extends RuntimeException {

    /**
     * Constructs a new DishNotFoundException with the given ID.
     *
     * @param id the ID of the dish that was not found
     */
    public DishNotFoundException(Long id) {
        super("Dish not found with ID: " + id);
    }
}
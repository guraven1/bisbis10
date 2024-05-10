package com.att.tdp.bisbis10.exception;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(Long restId, Long dishId) {
        super("Could not find Dish " + dishId + " in Restaurant " + restId);
    }
}
package com.att.tdp.bisbis10.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String id) {
        super("Order not found with ID: " + id);
    }
}

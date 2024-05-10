package com.att.tdp.bisbis10.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Could not find Order " + id);
    }
}
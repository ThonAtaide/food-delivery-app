package com.example.fooddeliveryapp.exceptions;

public class OrderNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Order from id %s not found.";

    public OrderNotFoundException(Long orderId) {
        super(String.format(ERROR_MESSAGE, orderId));
    }
}

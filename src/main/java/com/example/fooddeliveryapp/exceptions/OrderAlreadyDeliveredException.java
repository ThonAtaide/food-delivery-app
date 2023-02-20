package com.example.fooddeliveryapp.exceptions;

public class OrderAlreadyDeliveredException extends RuntimeException {

    private static final String ERROR_MESSAGE = "The Order from id %s was already delivered.";

    public OrderAlreadyDeliveredException(Long orderId) {
        super(String.format(ERROR_MESSAGE, orderId));
    }
}

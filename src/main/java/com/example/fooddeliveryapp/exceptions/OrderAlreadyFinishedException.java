package com.example.fooddeliveryapp.exceptions;

public class OrderAlreadyFinishedException extends RuntimeException {

    private static final String ERROR_MESSAGE = "The Order from id %s is already finished.";

    public OrderAlreadyFinishedException(Long orderId) {
        super(String.format(ERROR_MESSAGE, orderId));
    }
}

package com.emazon.stock_service.domain.exception;

public class InvalidProductCreationException extends RuntimeException {
    public InvalidProductCreationException(String message) {
        super(message);
    }
}

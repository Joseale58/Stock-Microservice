package com.emazon.stock_service.domain.exception;

public class DataConstraintViolationException extends RuntimeException {
    public DataConstraintViolationException(String message) {
        super(message);
    }
}

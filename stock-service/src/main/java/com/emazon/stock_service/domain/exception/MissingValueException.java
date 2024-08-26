package com.emazon.stock_service.domain.exception;

public class MissingValueException extends RuntimeException {
    public MissingValueException(String field) {
        super(field + " no puede ser nulo");
    }
}

package com.emazon.stock_service.domain.exception;

public class DataConstraintViolationException extends RuntimeException {
    public DataConstraintViolationException(String field, Integer length) {
        super("La longitud de " + field + " debe ser máximo de: " + length.toString() + " caracteres");
    }
}

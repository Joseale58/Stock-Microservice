package com.emazon.stock_service.domain.exception;

public class MinimumDataConstraintViolationException extends RuntimeException {
    public MinimumDataConstraintViolationException(String field, Integer length) {
        super("La longitud de " + field + " debe ser mínimo de: " + length.toString() + " caracteres");
    }
}

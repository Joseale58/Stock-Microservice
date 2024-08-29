package com.emazon.stock_service.infraestructure.exceptionhandler;

public enum ExceptionResponse {

    CATEGORY_NOT_FOUND("No se encontraron categorías con ese nombre"),
    CATEGORY_ALREADY_EXISTS("Ya existe una categoría con ese nombre");

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

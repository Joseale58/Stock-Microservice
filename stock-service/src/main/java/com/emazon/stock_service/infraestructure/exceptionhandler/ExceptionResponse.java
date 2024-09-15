package com.emazon.stock_service.infraestructure.exceptionhandler;

public enum ExceptionResponse {

    CATEGORY_NOT_FOUND_BY_NAME("No se encontraron categorías con ese nombre"),
    CATEGORY_NOT_FOUND_BY_ID("No se encontró la categoría con ese id"),
    CATEGORY_ALREADY_EXISTS("Ya existe una categoría con ese nombre"),
    BRAND_NOT_FOUND_BY_ID("No se encontró la marca con ese id"),
    PRODUCTS_NOT_FOUND("No se encontraron productos"),
    PRODUCT_ALREADY_EXISTS("Ya existe un producto con ese nombre"),;


    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

package com.emazon.stock_service.infraestructure.exceptionhandler;

import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.InvalidProductCreationException;
import com.emazon.stock_service.domain.exception.MinimumDataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.infraestructure.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "Mensaje:";

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCategoryAlreadyExistsException(
            CategoryAlreadyExistsException categoryAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CATEGORY_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundByNameException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(
            CategoryNotFoundByNameException categoryNotFoundByNameException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE,  ExceptionResponse.CATEGORY_NOT_FOUND_BY_NAME.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundByIdException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(
            CategoryNotFoundByIdException categoryNotFoundByIdException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE,  ExceptionResponse.CATEGORY_NOT_FOUND_BY_ID.getMessage()));
    }

    @ExceptionHandler(BrandNotFOundByIdException.class)
    public ResponseEntity<Map<String, String>> handleBrandNotFoundException(
            BrandNotFOundByIdException brandNotFOundByIdException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE,  ExceptionResponse.BRAND_NOT_FOUND_BY_ID.getMessage()));
    }

    @ExceptionHandler(ProductsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductsNotFoundException(
            ProductsNotFoundException productsNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE,  ExceptionResponse.PRODUCTS_NOT_FOUND.getMessage()));
    }

    //Exceptions handling from domain layer

    @ExceptionHandler(DataConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataConstraintViolationException(
            DataConstraintViolationException dataConstraintViolationException)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, dataConstraintViolationException.getMessage()));
    }

    @ExceptionHandler(MissingValueException.class)
    public ResponseEntity<Map<String, String>> handleMissingValueException(
            MissingValueException missingValueException)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, missingValueException.getMessage()));
    }

    @ExceptionHandler(MinimumDataConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleMinimumDataConstraintViolationException(
            MinimumDataConstraintViolationException minimumDataConstraintViolationException)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, minimumDataConstraintViolationException.getMessage()));
    }

    @ExceptionHandler(InvalidProductCreationException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCategoryException(
            InvalidProductCreationException invalidProductCreationException)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, invalidProductCreationException.getMessage()));
    }

    // Manejo de IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, illegalArgumentException.getMessage()));
    }
}

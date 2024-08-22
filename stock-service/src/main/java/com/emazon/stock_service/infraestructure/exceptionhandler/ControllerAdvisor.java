package com.emazon.stock_service.infraestructure.exceptionhandler;

import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.infraestructure.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.infraestructure.exception.CategoryNotFoundException;
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
                .body(Collections.singletonMap(MESSAGE, categoryAlreadyExistsException.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(
            CategoryNotFoundException categoryNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, categoryNotFoundException.getMessage()));
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

    // Manejo de IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, illegalArgumentException.getMessage()));
    }
}

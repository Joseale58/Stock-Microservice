package com.emazon.stock_service.infraestructure.input.rest;

import com.emazon.stock_service.application.dto.CategoryDto;
import com.emazon.stock_service.application.handler.ICategoryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;

    //To create a new category
    @PostMapping
    public ResponseEntity<String> saveCategory(@RequestBody CategoryDto categoryDto) {
        categoryHandler.saveCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se creó exitosamente la categoría: " + categoryDto.getName());
    }





}

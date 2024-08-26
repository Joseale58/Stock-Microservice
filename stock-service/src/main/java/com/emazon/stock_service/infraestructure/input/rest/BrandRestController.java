package com.emazon.stock_service.infraestructure.input.rest;

import com.emazon.stock_service.application.dto.BrandDto;
import com.emazon.stock_service.application.dto.CategoryDto;
import com.emazon.stock_service.application.handler.IBrandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandRestController {

    private final IBrandHandler brandHandler;

    //To create a new brand
    @PostMapping
    public ResponseEntity<String> saveCategory(@RequestBody BrandDto brandDto) {
        brandHandler.saveBrand(brandDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se creó exitosamente la marca: " + brandDto.getName());
    }



}

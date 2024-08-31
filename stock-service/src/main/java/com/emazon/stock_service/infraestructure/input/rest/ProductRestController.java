package com.emazon.stock_service.infraestructure.input.rest;

import com.emazon.stock_service.application.dto.ProductDtoRequest;
import com.emazon.stock_service.application.handler.ProductHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductHandler articleHandler;

    //To create a new brand
    @PostMapping
    public ResponseEntity<String> saveCategory(@RequestBody ProductDtoRequest productDtoRequest) {
        articleHandler.saveProduct(productDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se creó exitosamente la marca: " + productDtoRequest.getName());
    }

}

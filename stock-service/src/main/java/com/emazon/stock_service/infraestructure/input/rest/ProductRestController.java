package com.emazon.stock_service.infraestructure.input.rest;

import com.emazon.stock_service.application.dto.BrandDto;
import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.application.dto.ProductDtoRequest;
import com.emazon.stock_service.application.dto.ProductDtoResponse;
import com.emazon.stock_service.application.handler.ProductHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductHandler productHandler;

    //To get all products
    @GetMapping("/{page}/{pageSize}/{order}/{sort}")
    public ResponseEntity<CustomPageDto<ProductDtoResponse>> getAllProducts(@PathVariable Integer page, @PathVariable Integer pageSize, @PathVariable String order, @PathVariable String sort) {
        return ResponseEntity.ok(productHandler.getPaginatedProducts(page,pageSize, order, sort));
    }

    //To create a new product
    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody ProductDtoRequest productDtoRequest) {
        productHandler.saveProduct(productDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se creó exitosamente la marca: " + productDtoRequest.getName());
    }

}

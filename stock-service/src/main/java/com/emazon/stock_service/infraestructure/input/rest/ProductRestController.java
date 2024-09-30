package com.emazon.stock_service.infraestructure.input.rest;

import com.emazon.stock_service.application.dto.*;
import com.emazon.stock_service.application.handler.ProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "Get all products", description = "Get all products paginated with brand and category (only showing id and name for this last)")
    @GetMapping("/{page}/{pageSize}/{order}/{sort}")
    public ResponseEntity<CustomPageDto<ProductDtoResponse>> getAllProducts(@PathVariable @Parameter(description = "Page number to retrieve")Integer page, @PathVariable @Parameter(description = "Amount of elements per page") Integer pageSize, @PathVariable @Parameter(description = "Ascending or descending", example = "asc,desc") String order, @PathVariable @Parameter(description = "Sort by field", example = "name, brand, category") String sort) {
        return ResponseEntity.ok(productHandler.getPaginatedProducts(page,pageSize, order, sort));
    }

    //To get product by id
    @Operation(summary = "Get an unique product", description = "Get product by id)")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDtoResponse> getProductById(@PathVariable @Parameter(description = "Product id to retrieve") Long productId) {
        return ResponseEntity.ok(productHandler.getProductById(productId));
    }

    //To create a new product
    @Operation(summary = "Create a new product", description = "Create a new product")
    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody ProductDtoRequest productDtoRequest) {
        productHandler.saveProduct(productDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se creó exitosamente el producto: " + productDtoRequest.getName());
    }

    //To update a product
    @Operation(summary = "Update a product", description = "Update a product")
    @PatchMapping
    public ResponseEntity<String> updateProduct(@RequestBody UpdateStockDtoRequest updateStockDtoRequest) {
        productHandler.updateProduct(updateStockDtoRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

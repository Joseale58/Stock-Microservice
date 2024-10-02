package com.emazon.stock_service.infraestructure.input.rest;

import com.emazon.stock_service.application.dto.*;
import com.emazon.stock_service.application.handler.IProductHandler;
import com.emazon.stock_service.application.handler.ProductHandler;
import com.emazon.stock_service.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final IProductHandler productHandler;

    @Operation(summary = "Get all products", description = "Get all products paginated with brand and category (only showing id and name for this last)")
    @GetMapping()
    public ResponseEntity<CustomPageDto<ProductDtoResponse>> getAllProducts(
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE) Integer page,
            @RequestParam(defaultValue = Constants.DEFAULT_SIZE) Integer size,
            @RequestParam(defaultValue = Constants.DEFAULT_SORT_DIRECTION) String order,
            @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sort,
            @RequestParam(defaultValue = Constants.DEFAULT_CATEGORY_NAME) String categoryName,
            @RequestParam(defaultValue = Constants.DEFAULT_BRAND_NAME) String brandName,
            @RequestParam(required = false) List<Long> productsId

    ) {
        return ResponseEntity.ok(productHandler.getPaginatedProducts(page,size, order, sort, brandName, categoryName, productsId));
    }

    @Operation(summary = "Get an unique product", description = "Get product by id)")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDtoResponse> getProductById(@PathVariable @Parameter(description = "Product id to retrieve") Long productId) {
        return ResponseEntity.ok(productHandler.getProductById(productId));
    }

    @Operation(summary = "Create a new product", description = "Create a new product")
    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody ProductDtoRequest productDtoRequest) {
        productHandler.saveProduct(productDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Constants.SUCCESFULLY_CREATED + productDtoRequest.getName());
    }

    @Operation(summary = "Update a product", description = "Let add stock to a product")
    @PatchMapping("/addstock")
    public ResponseEntity<String> addStock(@RequestBody UpdateStockDtoRequest updateStockDtoRequest) {
        productHandler.addstock(updateStockDtoRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Update a product", description = "Let subtract stock to a product")
    @PatchMapping("/subtractstock")
    public ResponseEntity<String> subtractStock(@RequestBody UpdateStockDtoRequest updateStockDtoRequest) {
        productHandler.subtractstock(updateStockDtoRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

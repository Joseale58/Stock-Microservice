package com.emazon.stock_service.infraestructure.input.rest;

import com.emazon.stock_service.application.dto.BrandDto;
import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.application.handler.IBrandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandRestController {

    private final IBrandHandler brandHandler;

    //To paginated brands
    @Operation(summary = "Get all brands", description = "Get all brands paginated")
    @GetMapping("/{page}/{pageSize}/{order}")
    public ResponseEntity<CustomPageDto<BrandDto>> getAllBrands(@PathVariable @Parameter(description = "Page number to retrieve")Integer page, @PathVariable @Parameter(description = "Amount of elements per page")  Integer pageSize, @PathVariable @Parameter(description = "Ascending or descending", example = "asc,desc") String order) {
        return ResponseEntity.ok(brandHandler.getPaginatedCategories(page,pageSize, order));
    }

    //To create a new brand
    @Operation(summary = "Create a new brand", description = "Create a new brand")
    @PostMapping
    public ResponseEntity<String> saveCategory(@RequestBody BrandDto brandDto) {
        brandHandler.saveBrand(brandDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se creó exitosamente la marca: " + brandDto.getName());
    }



}

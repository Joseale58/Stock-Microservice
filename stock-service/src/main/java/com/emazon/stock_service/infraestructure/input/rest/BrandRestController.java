package com.emazon.stock_service.infraestructure.input.rest;

import com.emazon.stock_service.application.dto.BrandDto;
import com.emazon.stock_service.application.dto.CustomPageDto;
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

    //To paginated brands
    @GetMapping("/{page}/{pageSize}/{order}")
    public ResponseEntity<CustomPageDto<BrandDto>> getAllBrands(@PathVariable Integer page, @PathVariable Integer pageSize, @PathVariable String order) {
        return ResponseEntity.ok(brandHandler.getPaginatedCategories(page,pageSize, order));
    }

    //To create a new brand
    @PostMapping
    public ResponseEntity<String> saveCategory(@RequestBody BrandDto brandDto) {
        brandHandler.saveBrand(brandDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se creó exitosamente la marca: " + brandDto.getName());
    }



}

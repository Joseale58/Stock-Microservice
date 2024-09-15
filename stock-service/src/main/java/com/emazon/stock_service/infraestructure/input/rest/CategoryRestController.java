package com.emazon.stock_service.infraestructure.input.rest;

import com.emazon.stock_service.application.dto.CategoryDto;
import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.application.handler.ICategoryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    //To show a category
    @GetMapping("/{name}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String name) {
        return ResponseEntity.ok(categoryHandler.getCategoryByName(name));
    }

    //To show all categories
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return  ResponseEntity.ok(categoryHandler.getAllCategories());
    }

    //To show categories paginated
    @Operation(summary = "Get all categories", description = "Get all categories paginated")
    @GetMapping("/{page}/{pageSize}/{order}")
    public ResponseEntity<CustomPageDto<CategoryDto>> getAllCategories(@PathVariable @Parameter(description = "Page number to retrieve") Integer page, @PathVariable @Parameter(description = "Amount of elements per page") Integer pageSize, @PathVariable @Parameter(description = "Ascending or descending", example = "asc,desc")String order) {
        return ResponseEntity.ok(categoryHandler.getPaginatedCategories(page,pageSize, order));
    }


    //To create a new category
    @Operation(summary = "Create a new category", description = "Create a new category")
    @PostMapping
    public ResponseEntity<String> saveCategory(@RequestBody CategoryDto categoryDto) {
        categoryHandler.saveCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se creó exitosamente la categoría: " + categoryDto.getName());
    }





}

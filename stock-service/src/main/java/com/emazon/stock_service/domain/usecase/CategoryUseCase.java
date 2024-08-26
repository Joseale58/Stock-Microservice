package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort iCategoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort iCategoryPersistencePort) {
        this.iCategoryPersistencePort = iCategoryPersistencePort;
    }


    @Override
    public Category getCategoryByName(String name) {
        if(name.length() > 50){
            throw new IllegalArgumentException("El nombre de la categoría no puede ser mayor a 50 caracteres");
        }
        return this.iCategoryPersistencePort.getCategoryByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return this.iCategoryPersistencePort.getAllCategories();
    }

    @Override
    public CustomPage<Category> getPaginatedCategories(Integer page, Integer pageSize, String order) {

        // Validar que el número de página sea mayor o igual a 0
        if (page < 0) {
            throw new IllegalArgumentException("El número de página debe ser mayor o igual a 0");
        }

        // Validar que el tamaño de página sea mayor a 0 y menor o igual a un límite máximo
        int maxPageSize = 100;  // Definir un límite máximo razonable
        if (pageSize <= 0 || pageSize > maxPageSize) {
            throw new IllegalArgumentException("El tamaño de página debe ser mayor a 0 y menor o igual a " + maxPageSize);
        }

        // Validar que el orden sea ascendente o descendente
        if (!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order)) {
            throw new IllegalArgumentException("El parámetro de orden debe ser 'asc' o 'desc'.");
        }

        return this.iCategoryPersistencePort.getPaginatedCategories(page, pageSize, order);
    }

    @Override
    public void save(Category category) {
        if(category.getName() == null || category.getName().isEmpty()) {
            throw new MissingValueException("nombre de categoría");
        }
        if(category.getDescription() == null || category.getDescription().isEmpty()) {
            throw new MissingValueException("descripción");
        }
        if(category.getName().length() > 50){
            throw new DataConstraintViolationException("nombre", 50);
        }
        if (category.getDescription().length() > 90){
            throw new DataConstraintViolationException("descripción", 90);
        }
        this.iCategoryPersistencePort.save(category);
    }

    @Override
    public void update(Category category) {
        this.iCategoryPersistencePort.update(category);
    }

    @Override
    public void delete(Category category) {
        this.iCategoryPersistencePort.delete(category);
    }
}

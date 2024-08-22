package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort iCategoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort iCategoryPersistencePort) {
        this.iCategoryPersistencePort = iCategoryPersistencePort;
    }


    @Override
    public Category getCategoryByName(String name) {
        if(name.length() > 50){
            throw new MissingValueException("La longitud del nombre no puede ser mayor a 50 caracteres");
        }
        return this.iCategoryPersistencePort.getCategoryByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return this.iCategoryPersistencePort.getAllCategories();
    }

    @Override
    public List<Category> getPaginatedCategories(int page, int pageSize, String order) {

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
            throw new MissingValueException("El nombre de la categoría no puede ser nulo");
        }
        if(category.getDescription() == null || category.getDescription().isEmpty()) {
            throw new MissingValueException("La descripción de la categoría no puede ser nula");
        }
        if(category.getName().length() > 50){
            throw new DataConstraintViolationException("La longitud del nombre no puede ser mayor a 50 caracteres");
        }
        if (category.getDescription().length() > 90){
            throw new DataConstraintViolationException("La longitud de la descripción no puede ser mayor a 90 caracteres");
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

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

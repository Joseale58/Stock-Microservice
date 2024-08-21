package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.ICategoryServicePort;
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

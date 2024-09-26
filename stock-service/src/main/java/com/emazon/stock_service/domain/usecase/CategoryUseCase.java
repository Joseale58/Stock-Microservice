package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.utils.Constants;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort iCategoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort iCategoryPersistencePort) {
        this.iCategoryPersistencePort = iCategoryPersistencePort;
    }


    @Override
    public Category getCategoryByName(String name) {
        if(name.length() > Constants.MAX_CATEGORY_NAME_LENGTH){
            throw new IllegalArgumentException(Constants.MAX_CATEGORY_NAME_LENGTH_EXCEPTION);
        }
        return this.iCategoryPersistencePort.getCategoryByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return this.iCategoryPersistencePort.getAllCategories();
    }

    @Override
    public CustomPage<Category> getPaginatedCategories(Integer page, Integer pageSize, String order) {

        if (page < Constants.PAGE_MINIMUM_INDEX) {
            throw new IllegalArgumentException(Constants.PAGE_MUST_BE_POSITIVE_EXCEPTION);
        }
        if (pageSize <= Constants.PAGE_MIN_SIZE_ILLEGAL || pageSize > Constants.PAGE_MAX_SIZE) {
            throw new IllegalArgumentException(Constants.PAGE_SIZE_RANGE_EXCEPTION);
        }
        if (!Constants.PAGE_ASC_OPTION.equalsIgnoreCase(order) && !Constants.PAGE_DESC_OPTION.equalsIgnoreCase(order)) {
            throw new IllegalArgumentException(Constants.PAGE_ORDER_OPTION_EXCEPTION);
        }

        return this.iCategoryPersistencePort.getPaginatedCategories(page, pageSize, order);
    }

    @Override
    public void save(Category category) {
        if(category.getName() == null || category.getName().isEmpty()) {
            throw new MissingValueException(Constants.MISSING_CATEGORY_NAME_EXCEPTION);
        }
        if(category.getDescription() == null || category.getDescription().isEmpty()) {
            throw new MissingValueException(Constants.MISSING_CATEGORY_DESCRIPTION_EXCEPTION);
        }
        if(category.getName().length() > Constants.MAX_CATEGORY_NAME_LENGTH){
            throw new DataConstraintViolationException(Constants.MAX_CATEGORY_NAME_LENGTH_EXCEPTION);
        }
        if (category.getDescription().length() > Constants.MAX_CATEGORY_DESCRIPTION_LENGTH){
            throw new DataConstraintViolationException(Constants.MAX_CATEGORY_DESCRIPTION_LENGTH_EXCEPTION);
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

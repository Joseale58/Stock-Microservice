package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.utils.Constants;

public class BrandUseCase implements IBrandServicePort {


    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public CustomPage<Brand> getPaginatedBrands(Integer page, Integer pageSize, String order) {
        if (page < Constants.PAGE_MINIMUM_INDEX) {
            throw new IllegalArgumentException(Constants.PAGE_MUST_BE_POSITIVE_EXCEPTION);
        }
        if (pageSize <= Constants.PAGE_MIN_SIZE_ILLEGAL || pageSize > Constants.PAGE_MAX_SIZE) {
            throw new IllegalArgumentException(Constants.PAGE_SIZE_RANGE_EXCEPTION);
        }
        if (!Constants.PAGE_ASC_OPTION.equalsIgnoreCase(order) && !Constants.PAGE_DESC_OPTION.equalsIgnoreCase(order)) {
            throw new IllegalArgumentException(Constants.PAGE_ORDER_OPTION_EXCEPTION);
        }
        return this.brandPersistencePort.getPaginatedBrands(page, pageSize, order);
    }

    @Override
    public void save(Brand brand) {
        if(brand.getName() == null || brand.getName().isEmpty()) {
            throw new MissingValueException(Constants.MISSING_BRAND_NAME_EXCEPTION);
        }
        if(brand.getDescription() == null || brand.getDescription().isEmpty()) {
            throw new MissingValueException(Constants.MISSING_BRAND_DESCRIPTION_EXCEPTION);
        }
        if(brand.getName().length() > Constants.MAX_BRAND_NAME_LENGTH){
            throw new DataConstraintViolationException(Constants.MAX_BRAND_NAME_LENGTH_EXCEPTION);
        }
        if (brand.getDescription().length() > Constants.MAX_BRAND_DESCRIPTION_LENGTH){
            throw new DataConstraintViolationException(Constants.MAX_BRAND_DESCRIPTION_LENGTH_EXCEPTION);
        }
        this.brandPersistencePort.save(brand);
    }
}

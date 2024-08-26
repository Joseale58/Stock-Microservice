package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;

public class BrandUseCase implements IBrandServicePort {


    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void save(Brand brand) {
        if(brand.getName() == null || brand.getName().isEmpty()) {
            throw new MissingValueException("nombre de marca");
        }
        if(brand.getDescription() == null || brand.getDescription().isEmpty()) {
            throw new MissingValueException("descripción");
        }
        if(brand.getName().length() > 50){
            throw new DataConstraintViolationException("nombre",50);
        }
        if (brand.getDescription().length() > 120){
            throw new DataConstraintViolationException("descripcion",120);
        }
        this.brandPersistencePort.save(brand);
    }
}

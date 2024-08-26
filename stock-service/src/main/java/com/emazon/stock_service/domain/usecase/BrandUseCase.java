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
            throw new MissingValueException("El nombre de la marca no puede ser nulo");
        }
        if(brand.getDescription() == null || brand.getDescription().isEmpty()) {
            throw new MissingValueException("La descripción de la marca no puede ser nula");
        }
        if(brand.getName().length() > 50){
            throw new DataConstraintViolationException("La longitud del nombre no puede ser mayor a 50 caracteres");
        }
        if (brand.getDescription().length() > 120){
            throw new DataConstraintViolationException("La longitud de la descripción no puede ser mayor a 120 caracteres");
        }
        this.brandPersistencePort.save(brand);
    }
}

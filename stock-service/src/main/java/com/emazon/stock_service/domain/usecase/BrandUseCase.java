package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

public class BrandUseCase implements IBrandServicePort {


    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public CustomPage<Brand> getPaginatedBrands(Integer page, Integer pageSize, String order) {
        if (page < 0) {
            throw new IllegalArgumentException("El número de página debe ser mayor o igual a 0");
        }
        int maxPageSize = 100;
        if (pageSize <= 0 || pageSize > maxPageSize) {
            throw new IllegalArgumentException("El tamaño de página debe ser mayor a 0 y menor o igual a " + maxPageSize);
        }

        if (!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order)) {
            throw new IllegalArgumentException("El parámetro de orden debe ser 'asc' o 'desc'.");
        }

        return this.brandPersistencePort.getPaginatedBrands(page, pageSize, order);
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

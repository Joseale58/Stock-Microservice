package com.emazon.stock_service.infraestructure.output.jpa.adapter;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.infraestructure.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.IBrandEntityMapper;
import com.emazon.stock_service.infraestructure.output.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public void save(Brand brand) {
        //Validating that the category doesn't exist at DB
        if(brandRepository.findByName(brand.getName()).isPresent()){
            throw new CategoryAlreadyExistsException("Esta marca: " + brand.getName() + ", ya existe");
        }
        brandRepository.save(brandEntityMapper.toBrandEntity(brand));
    }

}

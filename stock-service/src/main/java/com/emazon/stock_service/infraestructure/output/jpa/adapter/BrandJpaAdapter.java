package com.emazon.stock_service.infraestructure.output.jpa.adapter;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.infraestructure.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.infraestructure.exception.CategoryNotFoundException;
import com.emazon.stock_service.infraestructure.output.jpa.entity.BrandEntity;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.IBrandEntityMapper;
import com.emazon.stock_service.infraestructure.output.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public CustomPage<Brand> getPaginatedBrands(Integer page, Integer pageSize, String order) {
        final Pageable pageable; // Declare var out of if-block
        if(order.equals("asc")){
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "name"));
        }

        Page<BrandEntity> brandEntityPage = brandRepository.findAll(pageable);
        List<BrandEntity> brandEntityList = brandEntityPage.getContent();

        if (brandEntityList.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        return new CustomPage<>(
                brandEntityMapper.toBrandList(brandEntityList),
                brandEntityPage.getTotalElements(),
                brandEntityPage.getTotalPages(),
                brandEntityPage.getNumber(),
                order.equals("asc"),
                brandEntityPage.isEmpty()
        );
    }

    @Override
    public void save(Brand brand) {
        //Validating that the category doesn't exist at DB
        if(brandRepository.findByName(brand.getName()).isPresent()){
            throw new CategoryAlreadyExistsException("Esta marca: " + brand.getName() + ", ya existe");
        }
        brandRepository.save(brandEntityMapper.toBrandEntity(brand));
    }

}

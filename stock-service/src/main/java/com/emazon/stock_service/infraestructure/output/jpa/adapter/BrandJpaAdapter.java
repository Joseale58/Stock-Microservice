package com.emazon.stock_service.infraestructure.output.jpa.adapter;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.infraestructure.exception.BrandNotFoundByIdException;
import com.emazon.stock_service.infraestructure.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.infraestructure.exception.CategoryNotFoundByNameException;
import com.emazon.stock_service.infraestructure.output.jpa.entity.BrandEntity;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.IBrandEntityMapper;
import com.emazon.stock_service.infraestructure.output.jpa.repository.IBrandRepository;
import com.emazon.stock_service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public Brand getBrandById(Long id) {
        Optional<BrandEntity> brandEntityOptional = brandRepository.findById(id);
        if(brandEntityOptional.isEmpty()) {
            throw new BrandNotFoundByIdException();
        }
        return brandEntityMapper.toBrand(brandEntityOptional.get());
    }

    @Override
    public CustomPage<Brand> getPaginatedBrands(Integer page, Integer pageSize, String order) {
        final Pageable pageable;
        if(order.equals(Constants.PAGE_ASC_OPTION)){
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, Constants.PAGE_NAME_OPTION));
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, Constants.PAGE_NAME_OPTION));
        }

        Page<BrandEntity> brandEntityPage = brandRepository.findAll(pageable);
        List<BrandEntity> brandEntityList = brandEntityPage.getContent();

        if (brandEntityList.isEmpty()) {
            throw new CategoryNotFoundByNameException();
        }

        return new CustomPage<>(
                brandEntityMapper.toBrandList(brandEntityList),
                brandEntityPage.getTotalElements(),
                brandEntityPage.getTotalPages(),
                brandEntityPage.getNumber(),
                order.equals(Constants.PAGE_ASC_OPTION),
                brandEntityPage.isEmpty()
        );
    }

    @Override
    public void save(Brand brand) {
        if(brandRepository.findByName(brand.getName()).isPresent()){
            throw new CategoryAlreadyExistsException(Constants.BRAND_ALREADY_EXISTS_EXCEPTION);
        }
        brandRepository.save(brandEntityMapper.toBrandEntity(brand));
    }

    @Override
    public Boolean existsByName(String brandName) {
        return brandRepository.findByName(brandName).isPresent();
    }

}

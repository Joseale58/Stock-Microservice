package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

public interface IBrandPersistencePort {

    Brand getBrandById(Long id);

    CustomPage<Brand> getPaginatedBrands(Integer page, Integer pageSize, String order);

    void save (Brand brand);
}

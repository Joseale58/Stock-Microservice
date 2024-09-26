package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

public interface IBrandServicePort {


    CustomPage<Brand> getPaginatedBrands(Integer page, Integer pageSize, String order);

    void save (Brand brand);
}

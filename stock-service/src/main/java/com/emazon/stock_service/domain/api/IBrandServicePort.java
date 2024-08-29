package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

public interface IBrandServicePort {


    //To paginate brands
    CustomPage<Brand> getPaginatedBrands(Integer page, Integer pageSize, String order);

    //To create a new brand
    void save (Brand brand);
}

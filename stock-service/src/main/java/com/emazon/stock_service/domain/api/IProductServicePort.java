package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

public interface IProductServicePort {

    //To show product
    CustomPage<Product> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort);

    //To create a new product
    void save(Product product);
}

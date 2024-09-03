package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

public interface IProductPersistencePort {

    //To show product
    CustomPage<Product> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort);

    void save(Product product);
}

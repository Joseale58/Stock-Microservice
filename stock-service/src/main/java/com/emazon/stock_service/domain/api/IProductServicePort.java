package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Product;

public interface IProductServicePort {

    //To create a new article
    void save(Product product);
}

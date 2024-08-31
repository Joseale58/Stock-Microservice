package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Product;

public interface IProductPersistencePort {

    void save(Product product);
}

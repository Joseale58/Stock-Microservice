package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Brand;

public interface IBrandPersistencePort {

    //To create a new brand
    void save (Brand brand);
}

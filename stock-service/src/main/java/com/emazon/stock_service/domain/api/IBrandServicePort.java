package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Brand;

public interface IBrandServicePort {

    //To create a new brand
    void save (Brand brand);
}

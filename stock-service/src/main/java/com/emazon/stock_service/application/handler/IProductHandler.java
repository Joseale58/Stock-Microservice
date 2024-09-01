package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.ProductDtoRequest;

public interface IProductHandler {

    void saveProduct (ProductDtoRequest productDtoRequest);
}

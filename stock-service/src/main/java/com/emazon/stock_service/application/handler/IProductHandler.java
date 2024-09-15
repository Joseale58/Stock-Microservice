package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.application.dto.ProductDtoRequest;
import com.emazon.stock_service.application.dto.ProductDtoResponse;
import com.emazon.stock_service.application.dto.UpdateStockDtoRequest;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

public interface IProductHandler {

    CustomPageDto<ProductDtoResponse> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort);
    void saveProduct (ProductDtoRequest productDtoRequest);
    void updateProduct(UpdateStockDtoRequest updateStockDtoRequest);
}

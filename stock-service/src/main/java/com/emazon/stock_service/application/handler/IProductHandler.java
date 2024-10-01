package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.application.dto.ProductDtoRequest;
import com.emazon.stock_service.application.dto.ProductDtoResponse;
import com.emazon.stock_service.application.dto.UpdateStockDtoRequest;

public interface IProductHandler {

    CustomPageDto<ProductDtoResponse> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort);
    void saveProduct (ProductDtoRequest productDtoRequest);
    void addstock(UpdateStockDtoRequest updateStockDtoRequest);
    void subtractstock(UpdateStockDtoRequest updateStockDtoRequest);
    ProductDtoResponse getProductById(Long productId);
}

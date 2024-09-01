package com.emazon.stock_service.application.mapper;

import com.emazon.stock_service.application.dto.ProductDtoResponse;
import com.emazon.stock_service.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductDtoResponseMapper {

    //Article to ArticleDtoResponse
    ProductDtoResponse toProductDtoResponse(Product product);
}

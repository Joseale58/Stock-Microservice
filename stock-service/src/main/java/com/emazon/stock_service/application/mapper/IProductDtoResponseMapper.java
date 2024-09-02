package com.emazon.stock_service.application.mapper;

import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.application.dto.ProductDtoResponse;
import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductDtoResponseMapper {

    //To ProductDtoResponse
    ProductDtoResponse toProductDtoResponse(Product product);
}

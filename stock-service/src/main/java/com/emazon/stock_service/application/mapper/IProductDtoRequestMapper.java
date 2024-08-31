package com.emazon.stock_service.application.mapper;

import com.emazon.stock_service.application.dto.ProductDtoRequest;
import com.emazon.stock_service.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductDtoRequestMapper {

    //ArticleDtoRequest to Article
    Product toProduct(ProductDtoRequest productDtoRequest);

}

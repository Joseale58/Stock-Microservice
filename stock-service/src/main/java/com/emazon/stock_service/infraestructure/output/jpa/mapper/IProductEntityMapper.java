package com.emazon.stock_service.infraestructure.output.jpa.mapper;


import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.infraestructure.output.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductEntityMapper {

    @Mapping(source="brand", target="brand")
    @Mapping(source="categories", target="category")
    ProductEntity toProductEntity(Product product);

    @Mapping(source="brand", target="brand")
    @Mapping(source="category", target="categories")
    Product toProduct(ProductEntity productEntity);

    List<Product> toProductList(List<ProductEntity> productEntityList);
}

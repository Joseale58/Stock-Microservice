package com.emazon.stock_service.infraestructure.output.jpa.mapper;


import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.infraestructure.output.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductEntityMapper {

    ProductEntity toProductEntity(Product product);

    Product toProduct(ProductEntity productEntity);
}

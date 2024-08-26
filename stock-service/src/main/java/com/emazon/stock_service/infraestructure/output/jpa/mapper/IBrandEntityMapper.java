package com.emazon.stock_service.infraestructure.output.jpa.mapper;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.infraestructure.output.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface IBrandEntityMapper {

    BrandEntity toBrandEntity(Brand brand);

    Brand toBrand(BrandEntity brandEntity);
}

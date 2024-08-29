package com.emazon.stock_service.application.mapper;

import com.emazon.stock_service.application.dto.BrandDto;
import com.emazon.stock_service.domain.model.Brand;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface IBrandDtoMapper {

    //BrandDto to Brand
    Brand toBrand(BrandDto brandDto);

    //Brand to BrandDto
    BrandDto toBrandDto(Brand brand);
}

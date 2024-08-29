package com.emazon.stock_service.application.mapper;

import com.emazon.stock_service.application.dto.BrandDto;
import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface IBrandPageDtoMapper {

    // Mapea de modelo a DTO
    CustomPageDto<BrandDto> toCustomPageDto(CustomPage<Brand> customPage);

}

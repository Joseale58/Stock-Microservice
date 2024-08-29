package com.emazon.stock_service.application.mapper;

import com.emazon.stock_service.application.dto.CategoryDto;
import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICustomPageDtoMapper {


    // Mapea de modelo a DTO
    CustomPageDto<CategoryDto> toCustomPageDto(CustomPage<Category> customPage);

}

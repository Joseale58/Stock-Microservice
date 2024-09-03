package com.emazon.stock_service.application.mapper;

import com.emazon.stock_service.application.dto.CategoryProductDtoResponse;
import com.emazon.stock_service.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryProductDtoMapper {
    CategoryProductDtoResponse toCategoryProductDtoResponse(Category category);
}
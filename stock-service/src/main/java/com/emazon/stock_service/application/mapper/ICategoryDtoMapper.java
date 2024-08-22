package com.emazon.stock_service.application.mapper;

import com.emazon.stock_service.application.dto.CategoryDto;
import com.emazon.stock_service.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryDtoMapper {

    //To convert from CategoryDto to Category
    Category toCategory(CategoryDto categoryDto);

    //To convert from CategoryDto to Category
    CategoryDto toCategoryDto(Category category);

}

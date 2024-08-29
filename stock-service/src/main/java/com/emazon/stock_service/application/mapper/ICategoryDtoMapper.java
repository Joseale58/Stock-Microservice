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

    //To get the whole list of categories as dto
    default List<CategoryDto> toResponseList(List<Category> categoryList){
        return categoryList.stream().map(category ->{
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setDescription(category.getDescription());
            return categoryDto;
        }).toList();
    }
}

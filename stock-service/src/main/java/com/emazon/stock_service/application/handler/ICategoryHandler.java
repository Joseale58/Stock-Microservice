package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.CategoryDto;
import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

import java.util.List;

public interface ICategoryHandler {

    CategoryDto getCategoryByName(String categoryName);

    List<CategoryDto> getAllCategories();

    CustomPageDto<CategoryDto> getPaginatedCategories(int page, int page_size, String order);

    void saveCategory(CategoryDto categorydto);

    void updateCategory(CategoryDto categorydto);

    void deleteCategory(CategoryDto categorydto);
}

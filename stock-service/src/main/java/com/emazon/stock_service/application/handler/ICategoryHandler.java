package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.CategoryDto;

import java.util.List;

public interface ICategoryHandler {

    CategoryDto getCategoryByName(String categoryName);

    List<CategoryDto> getAllCategories();

    List<CategoryDto> getPaginatedCategories(int page, int page_size, String order);

    void saveCategory(CategoryDto categorydto);

    void updateCategory(CategoryDto categorydto);

    void deleteCategory(CategoryDto categorydto);
}

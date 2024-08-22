package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.CategoryDto;

import java.util.List;

public interface ICategoryHandler {

    void saveCategory(CategoryDto categorydto);

    void updateCategory(CategoryDto categorydto);

    void deleteCategory(CategoryDto categorydto);
}

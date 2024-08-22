package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.CategoryDto;
import com.emazon.stock_service.application.mapper.ICategoryDtoMapper;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.model.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryDtoMapper categoryDtoMapper;


    @Override
    public CategoryDto getCategoryByName(String categoryName) {
        return categoryDtoMapper.toCategoryDto(categoryServicePort.getCategoryByName(categoryName));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryDtoMapper.toResponseList(categoryServicePort.getAllCategories());
    }

    @Override
    public List<CategoryDto> getPaginatedCategories(int page, int page_size, String order) {
        return categoryDtoMapper.toResponseList(categoryServicePort.getPaginatedCategories(page, page_size, order));
    }

    @Override
    public void saveCategory(CategoryDto categorydto) {
        Category category = categoryDtoMapper.toCategory(categorydto);
        categoryServicePort.save(category);
    }

    @Override
    public void updateCategory(CategoryDto categorydto) {
        Category category = categoryDtoMapper.toCategory(categorydto);
        categoryServicePort.update(category);
    }

    @Override
    public void deleteCategory(CategoryDto categorydto) {
        Category category = categoryDtoMapper.toCategory(categorydto);
        categoryServicePort.delete(category);
    }
}

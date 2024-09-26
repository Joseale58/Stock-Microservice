package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

import java.util.List;

public interface ICategoryPersistencePort {

    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    List<Category> getAllCategories();

    CustomPage<Category> getPaginatedCategories(Integer page, Integer pageSize, String order);

    void save(Category category);

    void update(Category category);

    void delete(Category category);
}

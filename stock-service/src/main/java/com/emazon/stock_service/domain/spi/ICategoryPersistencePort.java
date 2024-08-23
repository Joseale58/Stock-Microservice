package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

import java.util.List;

public interface ICategoryPersistencePort {
    //To show a category
    Category getCategoryByName(String name);
    //To show all categories
    List<Category> getAllCategories();
    //To paginate categories
    CustomPage<Category> getPaginatedCategories(int page, int pageSize, String order);
    //To create a new cat
    void save(Category category);
    //To update an existent cat
    void update(Category category);
    //To delete an existent cat
    void delete(Category category);
}

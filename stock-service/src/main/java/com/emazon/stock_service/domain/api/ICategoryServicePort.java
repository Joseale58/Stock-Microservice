package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {
   //To create a new cat
    void save(Category category);
    //To update an existent cat
    void update(Category category);
    //To delete an existent cat
    void delete(Category category);
}

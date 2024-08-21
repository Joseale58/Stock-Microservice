package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {

    //To create a new cat
    void save(Category category);
    //To update an existent cat
    void update(Category category);
    //To delete an existent cat
    void delete(Category category);
}

package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.IProductServicePort;
import com.emazon.stock_service.domain.exception.InvalidProductCreationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.exception.MinimumDataConstraintViolationException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.spi.IProductPersistencePort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort articlePersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public ProductUseCase(IProductPersistencePort articlePersistencePort, ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void save(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new MissingValueException("nombre de artículo");
        }
        if (product.getDescription() == null || product.getDescription().isEmpty()) {
            throw new MissingValueException("descripción");
        }
        if (product.getStock() == null) {
            throw new MissingValueException("cantidad");
        }
        if (product.getPrice() == null) {
            throw new MissingValueException("precio");
        }
        if (product.getBrand().getId() == null){
            throw new MissingValueException("marca");
        }
        if (product.getCategories() == null || product.getCategories().isEmpty()) {
            throw new MissingValueException("categorías");
        }
        if (product.getStock() < 0) {
            throw new MinimumDataConstraintViolationException("cantidad", 0);
        }
        if (product.getPrice() < 0) {
            throw new MinimumDataConstraintViolationException("precio", 0);
        }

        if (this.brandPersistencePort.getBrandById(product.getBrand().getId()) == null) {
            throw new InvalidProductCreationException("La marca con id " + product.getBrand().getId() + " no existe.");
        }
        List<Category> categories = product.getCategories();

        if (categories.isEmpty() || categories.size() > 3) {
            throw new InvalidProductCreationException("El producto debe tener entre 1 y 3 categorías.");
        }

        Set<Long> categoryIds = new HashSet<>();
        for (Category category : categories) {
            if(category.getId() == null){
                throw new MissingValueException("id de categoría");
            }
            if (this.categoryPersistencePort.getCategoryById(category.getId()) == null) {
                throw new InvalidProductCreationException("La categoría con id " + category.getId() + " no existe.");
            }
            if (!categoryIds.add(category.getId())) {
                throw new InvalidProductCreationException("El producto no puede tener categorías repetidas.");
            }
        }

        this.articlePersistencePort.save(product);
    }

}

package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.IProductServicePort;
import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.InvalidProductCreationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.spi.IProductPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.infraestructure.exception.ProductsNotFoundException;
import com.emazon.stock_service.utils.Constants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort, ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public CustomPage<Product> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort) {
        if (page < Constants.PAGE_MINIMUM_INDEX) {
            throw new IllegalArgumentException(Constants.PAGE_MUST_BE_POSITIVE_EXCEPTION);
        }
        if (pageSize <= Constants.PAGE_MIN_SIZE_ILLEGAL || pageSize > Constants.PAGE_MAX_SIZE) {
            throw new IllegalArgumentException(Constants.PAGE_SIZE_RANGE_EXCEPTION);
        }
        if (!Constants.PAGE_ASC_OPTION.equalsIgnoreCase(order) && !Constants.PAGE_DESC_OPTION.equalsIgnoreCase(order)) {
            throw new IllegalArgumentException(Constants.PAGE_ORDER_OPTION_EXCEPTION);
        }
        if (!Constants.PAGE_NAME_OPTION.equalsIgnoreCase(sort) && !Constants.PAGE_BRAND_OPTION.equalsIgnoreCase(sort) && !Constants.PAGE_CATEGORY_OPTION.equalsIgnoreCase(sort)){
            throw new IllegalArgumentException(Constants.PAGE_SORT_OPTION_EXCEPTION);
        }

        return this.productPersistencePort.getPaginatedProducts(page, pageSize, order, sort);
    }

    @Override
    public void save(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new MissingValueException(Constants.MISSING_PRODUCT_NAME_EXCEPTION);
        }
        if (product.getDescription() == null || product.getDescription().isEmpty()) {
            throw new MissingValueException(Constants.MISSING_PRODUCT_DESCRIPTION_EXCEPTION);
        }
        if (product.getStock() == null) {
            throw new MissingValueException(Constants.MISSING_PRODUCT_STOCK_EXCEPTION);
        }
        if (product.getPrice() == null) {
            throw new MissingValueException(Constants.MISSING_PRODUCT_PRICE_EXCEPTION);
        }
        if (product.getBrand().getId() == null){
            throw new MissingValueException(Constants.MISSING_PRODUCT_BRAND_EXCEPTION);
        }
        if (product.getCategories() == null || product.getCategories().isEmpty()) {
            throw new MissingValueException(Constants.MISSING_PRODUCT_CATEGORIES_EXCEPTION);
        }
        if (product.getStock() < Constants.INVALID_MINIMUM_STOCK_AMOUNT) {
            throw new DataConstraintViolationException(Constants.INVALID_MINIMUM_STOCK_AMOUNT_EXCEPTION);
        }
        if (product.getPrice() < Constants.INVALID_MINIMUM_PRICE_AMOUNT) {
            throw new DataConstraintViolationException(Constants.INVALID_MINIMUM_PRICE_AMOUNT_EXCEPTION);
        }

        if (this.brandPersistencePort.getBrandById(product.getBrand().getId()) == null) {
            throw new InvalidProductCreationException(Constants.BRAND_INVALID_ID_EXCEPTION);
        }
        List<Category> categories = product.getCategories();

        if (categories.isEmpty() || categories.size() > 3) {
            throw new InvalidProductCreationException(Constants.CATEGORY_INVALID_AMOUNT_EXCEPTION);
        }

        Set<Long> categoryIds = new HashSet<>();
        for (Category category : categories) {
            if(category.getId() == null){
                throw new MissingValueException(Constants.MISSING_PRODUCT_CATEGORIES_EXCEPTION);
            }
            if (this.categoryPersistencePort.getCategoryById(category.getId()) == null) {
                throw new InvalidProductCreationException(Constants.CATEGORY_INVALID_ID_EXCEPTION);
            }
            if (!categoryIds.add(category.getId())) {
                throw new InvalidProductCreationException(Constants.CATEGORY_DUPLICATED_EXCEPTION);
            }
        }

        this.productPersistencePort.save(product);
    }

    @Override
    public void update(Long productId, Integer quantity) {
        if (quantity < Constants.INVALID_MINIMUM_STOCK_AMOUNT) {
            throw new DataConstraintViolationException(Constants.INVALID_MINIMUM_STOCK_AMOUNT_EXCEPTION);
        }
        if(this.productPersistencePort.getProductById(productId) == null){
            throw new ProductsNotFoundException();
        }
        this.productPersistencePort.update(productId, quantity);
    }

    @Override
    public Product getProductById(Long productId) {
        Product product = this.productPersistencePort.getProductById(productId);
        if(product == null){
            throw new ProductsNotFoundException();
        } else {
            return product;
        }
    }

}

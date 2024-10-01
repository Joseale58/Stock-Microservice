package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.util.pageable.CustomPage;

public interface IProductServicePort {

    CustomPage<Product> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort);

    void save(Product product);

    void addstock(Long productId, Integer quantity);

    void subtractstock(Long productId, Integer quantity);

    Product getProductById(Long productId);
}

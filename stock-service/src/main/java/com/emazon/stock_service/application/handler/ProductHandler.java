package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.ProductDtoRequest;
import com.emazon.stock_service.application.mapper.IProductDtoRequestMapper;
import com.emazon.stock_service.application.mapper.IProductDtoResponseMapper;
import com.emazon.stock_service.domain.api.IProductServicePort;
import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {


    private final IProductServicePort articleServicePort;
    private final IProductDtoRequestMapper articleDtoRequestMapper;
    private final IProductDtoResponseMapper articleDtoResponseMapper;

    @Override
    public void saveProduct(ProductDtoRequest productDtoRequest) {

        Product product = articleDtoRequestMapper.toProduct(productDtoRequest);

        List<Category> categories = productDtoRequest.getCategoryId().stream()
                .map(categoryId -> {
                    Category category = new Category();
                    category.setId(categoryId);
                    return category;
                }).toList();
        product.setCategories(categories);

        Brand brand = new Brand();
        brand.setId(productDtoRequest.getBrandId());
        product.setBrand(brand);

        this.articleServicePort.save(product);
    }
}

package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.application.dto.ProductDtoRequest;
import com.emazon.stock_service.application.dto.ProductDtoResponse;
import com.emazon.stock_service.application.dto.UpdateStockDtoRequest;
import com.emazon.stock_service.application.mapper.IPageDtoMapper;
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


    private final IProductServicePort productServicePort;
    private final IProductDtoRequestMapper productDtoRequestMapper;
    private final IProductDtoResponseMapper productDtoResponseMapper;
    private final IPageDtoMapper pageDtoMapper;

    @Override
    public CustomPageDto<ProductDtoResponse> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort) {
        return pageDtoMapper.toProductDtoPageCustom(this.productServicePort.getPaginatedProducts(page, pageSize, order, sort));
    }

    @Override
    public void saveProduct(ProductDtoRequest productDtoRequest) {

        Product product = productDtoRequestMapper.toProduct(productDtoRequest);

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

        this.productServicePort.save(product);
    }

    @Override
    public void addstock(UpdateStockDtoRequest updateStockDtoRequest) {
        this.productServicePort.addstock(updateStockDtoRequest.getProductId(), updateStockDtoRequest.getQuantity());
    }

    @Override
    public void subtractstock(UpdateStockDtoRequest updateStockDtoRequest) {
        this.productServicePort.subtractstock(updateStockDtoRequest.getProductId(), updateStockDtoRequest.getQuantity());
    }

    @Override
    public ProductDtoResponse getProductById(Long productId) {
        return this.productDtoResponseMapper.toProductDtoResponse(this.productServicePort.getProductById(productId));
    }
}

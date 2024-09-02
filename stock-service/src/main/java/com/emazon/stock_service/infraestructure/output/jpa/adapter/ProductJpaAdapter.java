package com.emazon.stock_service.infraestructure.output.jpa.adapter;

import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.spi.IProductPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.infraestructure.output.jpa.entity.ProductEntity;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.IProductEntityMapper;
import com.emazon.stock_service.infraestructure.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public CustomPage<Product> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort sortby = Sort.by(direction, sort);
        Pageable pageable = PageRequest.of(page, pageSize, sortby);
        Page<ProductEntity> productEnityPage = productRepository.findAll(pageable);
        return new CustomPage<>(
                productEntityMapper.toProductList(productEnityPage.getContent()),
                productEnityPage.getTotalElements(),
                productEnityPage.getTotalPages(),
                productEnityPage.getNumber(),
                order.equals("asc"),
                productEnityPage.isEmpty()
        );
    }



    @Override
    public void save(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new RuntimeException("This article already exists");
        }
        productRepository.save(productEntityMapper.toProductEntity(product));
    }

}

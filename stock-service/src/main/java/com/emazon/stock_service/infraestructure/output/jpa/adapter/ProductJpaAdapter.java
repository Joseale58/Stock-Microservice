package com.emazon.stock_service.infraestructure.output.jpa.adapter;

import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.spi.IProductPersistencePort;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.IProductEntityMapper;
import com.emazon.stock_service.infraestructure.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {

    private final IProductRepository articleRepository;
    private final IProductEntityMapper articleEntityMapper;

    @Override
    public void save(Product product) {
        if (articleRepository.findByName(product.getName()).isPresent()) {
            throw new RuntimeException("This article already exists");
        }
        articleRepository.save(articleEntityMapper.toProductEntity(product));
    }

}

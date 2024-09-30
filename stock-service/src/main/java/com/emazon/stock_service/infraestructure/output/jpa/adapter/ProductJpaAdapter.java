package com.emazon.stock_service.infraestructure.output.jpa.adapter;

import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.spi.IProductPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.infraestructure.exception.ProductAlreadyExistsException;
import com.emazon.stock_service.infraestructure.exception.ProductsNotFoundException;
import com.emazon.stock_service.infraestructure.output.jpa.entity.CategoryEntity;
import com.emazon.stock_service.infraestructure.output.jpa.entity.ProductEntity;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.IPageMapper;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.IProductEntityMapper;
import com.emazon.stock_service.infraestructure.output.jpa.repository.ICategoryRepository;
import com.emazon.stock_service.infraestructure.output.jpa.repository.IProductRepository;
import com.emazon.stock_service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {


    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final IPageMapper pageMapper;

    @Override
    public CustomPage<Product> getPaginatedProducts(Integer page, Integer pageSize, String order, String sort) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Pageable pageable;
        if (Constants.PAGE_CATEGORY_OPTION.equalsIgnoreCase(sort)) {
            pageable = PageRequest.of(page, pageSize, Sort.by(direction, Constants.PAGE_CATEGORY_SORT_OPTION));
        } else if (Constants.PAGE_BRAND_OPTION.equalsIgnoreCase(sort)) {
            pageable = PageRequest.of(page, pageSize, Sort.by(direction, Constants.PAGE_BRAND_SORT_OPTION));
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.by(direction, sort));
        }
        Page<ProductEntity> productEntityPage = productRepository.findAll(pageable);
        List<ProductEntity> productList = productEntityPage.getContent();
        if (productList.isEmpty()) {
            throw new ProductsNotFoundException();
        }
        return pageMapper.toProductCustomPage(productEntityPage);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productEntityMapper::toProduct)
                .orElseThrow(() -> new ProductsNotFoundException());
    }

    @Override
    public void save(Product product) {
        if (this.productRepository.findByName(product.getName()).isPresent()) {
            throw new ProductAlreadyExistsException();
        }
        ProductEntity productEntity = productEntityMapper.toProductEntity(product);
        List<CategoryEntity> categoryEntities = product.getCategories().stream()
                .map(category -> categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new IllegalArgumentException(Constants.CATEGORY_INVALID_ID_EXCEPTION)))
                .toList();
        productEntity.setCategories(categoryEntities);
        this.productRepository.save(productEntity);
    }

    @Override
    public void update(Long productId, Integer quantity) {
        Optional<ProductEntity> productEntity= productRepository.findById(productId);
        if(productEntity.isPresent()){
            ProductEntity product = productEntity.get();
            product.setStock(product.getStock() + quantity);
            productRepository.save(product);
        }
    }



}

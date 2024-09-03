package com.emazon.stock_service.infraestructure.output.jpa.adapter;

import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.spi.IProductPersistencePort;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.infraestructure.exception.ProductsNotFoundException;
import com.emazon.stock_service.infraestructure.output.jpa.entity.CategoryEntity;
import com.emazon.stock_service.infraestructure.output.jpa.entity.ProductEntity;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.IPageMapper;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.IProductEntityMapper;
import com.emazon.stock_service.infraestructure.output.jpa.repository.ICategoryRepository;
import com.emazon.stock_service.infraestructure.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


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

        if ("category".equalsIgnoreCase(sort)) {
            // Si el campo de ordenación es "category", hacer el join y ordenar por nombre de categoría
            pageable = PageRequest.of(page, pageSize, Sort.by(direction, "category.name")); // Reemplaza "categories.name" con el nombre correcto del campo en la relación Category
        } else if ("brand".equalsIgnoreCase(sort)) {
            // Si el campo de ordenación es "brand", hacer el join y ordenar por nombre de la marca
            pageable = PageRequest.of(page, pageSize, Sort.by(direction, "brand.name")); // Asumiendo que 'name' es el campo en la entidad Brand por el que deseas ordenar
        } else {
            // Si es otro campo, ordenar normalmente
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
    public void save(Product product) {

        if (this.productRepository.findByName(product.getName()).isPresent()) {
            throw new RuntimeException("Este producto ya existe");
        }

        ProductEntity productEntity = productEntityMapper.toProductEntity(product);

        //Is necessary to loop through the categories and get the category from the DB to save at a list and then set it to the product

        List<CategoryEntity> categoryEntities = product.getCategories().stream()
                .map(category -> categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Category with ID " + category.getId() + " not found")))
                .toList();

        productEntity.setCategory(categoryEntities);

        this.productRepository.save(productEntity);
    }

}

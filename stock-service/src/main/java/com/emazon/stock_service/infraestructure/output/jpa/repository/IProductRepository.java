package com.emazon.stock_service.infraestructure.output.jpa.repository;

import com.emazon.stock_service.infraestructure.output.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findByName(String productName);
}

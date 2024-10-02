package com.emazon.stock_service.infraestructure.output.jpa.repository;

import com.emazon.stock_service.infraestructure.output.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity,Long>, JpaSpecificationExecutor<ProductEntity> {
    Optional<ProductEntity> findByName(String productName);

    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.categories")
    List<ProductEntity> findAllWithCategories();
}

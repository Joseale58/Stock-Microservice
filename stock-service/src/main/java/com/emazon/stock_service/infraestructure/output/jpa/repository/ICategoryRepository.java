package com.emazon.stock_service.infraestructure.output.jpa.repository;

import com.emazon.stock_service.infraestructure.output.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {

    //Defining some specifics methods thru derivation methods
    Optional<CategoryEntity> findByName(String categoryName);

    void deleteByName(String categoryName);
}

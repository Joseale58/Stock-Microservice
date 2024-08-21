package com.emazon.stock_service.infraestructure.output.jpa.repository;

import com.emazon.stock_service.infraestructure.output.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {


}

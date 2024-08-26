package com.emazon.stock_service.infraestructure.output.jpa.repository;

import com.emazon.stock_service.infraestructure.output.jpa.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {

    Optional<BrandEntity> findByName(String brandName);

}

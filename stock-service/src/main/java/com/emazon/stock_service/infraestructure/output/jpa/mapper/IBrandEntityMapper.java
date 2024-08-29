package com.emazon.stock_service.infraestructure.output.jpa.mapper;

import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.infraestructure.output.jpa.entity.BrandEntity;
import com.emazon.stock_service.infraestructure.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel="spring")
public interface IBrandEntityMapper {

    BrandEntity toBrandEntity(Brand brand);

    Brand toBrand(BrandEntity brandEntity);

    List<Brand> toBrandList(List<BrandEntity> brandEntityList);

}

package com.emazon.stock_service.infraestructure.output.jpa.mapper;


import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.infraestructure.output.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE,
        uses = {IProductEntityMapper.class})
public interface IPageMapper {

    IProductEntityMapper PRODUCT_ENTITY_MAPPER = org.mapstruct.factory.Mappers.getMapper(IProductEntityMapper.class);

    default CustomPage<Product> toProductCustomPage(Page<ProductEntity> page){
        CustomPage<Product> customPage = new CustomPage<>();
        List<Product> products = page.getContent().stream()
                .map(PRODUCT_ENTITY_MAPPER::toProduct)
                .toList();
        customPage.setContent(products);
        customPage.setCurrentPage(page.getNumber());
        customPage.setTotalPages(page.getTotalPages());
        customPage.setTotalElements(page.getTotalElements());
        boolean isAscending = page.getSort().stream()
                .allMatch(order -> order.getDirection().isAscending());

        customPage.setAscending(isAscending);
        customPage.setEmpty(page.isEmpty());
        return customPage;
    }
}

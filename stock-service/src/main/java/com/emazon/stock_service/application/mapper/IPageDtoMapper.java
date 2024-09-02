package com.emazon.stock_service.application.mapper;
import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.application.dto.ProductDtoResponse;
import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {IProductDtoResponseMapper.class})
public interface IPageDtoMapper {

    IProductDtoResponseMapper productDtoResponseMapper = Mappers.getMapper(IProductDtoResponseMapper.class);

    default CustomPageDto<ProductDtoResponse> toProductDtoPageCustom(CustomPage<Product> page) {
        CustomPageDto<ProductDtoResponse> customPage = new CustomPageDto<>();
        List<ProductDtoResponse> products = page.getContent().stream()
                .map(productDtoResponseMapper::toProductDtoResponse)
                .toList();
        customPage.setContent(products);
        customPage.setTotalElements((int)page.getTotalElements());
        customPage.setTotalPages(page.getTotalPages());
        customPage.setCurrentPage(page.getCurrentPage());
        customPage.setAscending(page.isAscending());
        customPage.setEmpty(page.isEmpty());
        return customPage;
    }
}

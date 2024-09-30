package com.emazon.stock_service.application.mapper;
import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.application.dto.ProductDtoResponse;
import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {IProductDtoResponseMapper.class,ICategoryProductDtoMapper.class})
public interface IPageDtoMapper {

    IProductDtoResponseMapper PRODUCT_DTO_RESPONSE_MAPPER = Mappers.getMapper(IProductDtoResponseMapper.class);
    ICategoryProductDtoMapper CATEGORY_DTO_MAPPER = Mappers.getMapper(ICategoryProductDtoMapper.class);




    default CustomPageDto<ProductDtoResponse> toProductDtoPageCustom(CustomPage<Product> page) {
        CustomPageDto<ProductDtoResponse> customPage = new CustomPageDto<>();
//        List<ProductDtoResponse> products = page.getContent().stream()
//                .map(PRODUCT_DTO_RESPONSE_MAPPER::toProductDtoResponse)
//                .toList();
        List<ProductDtoResponse> products = page.getContent().stream()
                .map(product -> {
                    ProductDtoResponse productDto = PRODUCT_DTO_RESPONSE_MAPPER.toProductDtoResponse(product);

                    // Mapeo manual de categorías
                    if (product.getCategories() != null) {
                        productDto.setCategories(
                                product.getCategories().stream()
                                        .map(CATEGORY_DTO_MAPPER::toCategoryProductDtoResponse)
                                        .collect(Collectors.toList())
                        );
                    }

                    return productDto;
                })
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

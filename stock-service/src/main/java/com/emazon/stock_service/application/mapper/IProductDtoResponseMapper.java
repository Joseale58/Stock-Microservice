package com.emazon.stock_service.application.mapper;
import com.emazon.stock_service.application.dto.ProductDtoResponse;
import com.emazon.stock_service.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductDtoResponseMapper {

    //To ProductDtoResponse
    ProductDtoResponse toProductDtoResponse(Product product);
}

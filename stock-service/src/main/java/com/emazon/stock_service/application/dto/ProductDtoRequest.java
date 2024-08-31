package com.emazon.stock_service.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductDtoRequest {

    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private Long brandId;
    private List<Long> categoryId;
}

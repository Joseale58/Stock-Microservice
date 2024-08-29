package com.emazon.stock_service.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
// It's the ('flat') way how we expect to capture JSON to convert to a Java object and visc
    private Long id;
    private String name;
    private String description;

}

package com.emazon.stock_service.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateStockDtoRequest {
    private Long productId;
    private Integer quantity;

}

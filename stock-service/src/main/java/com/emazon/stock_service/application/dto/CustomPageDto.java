package com.emazon.stock_service.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomPageDto<T> {
    private List<T> content;
    private Integer totalElements;
    private Integer totalPages;
    private Integer currentPage;
    private Boolean ascending;
    private Boolean empty;
}

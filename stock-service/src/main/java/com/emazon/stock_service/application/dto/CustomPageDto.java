package com.emazon.stock_service.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomPageDto<T> {
    private List<T> content;
    private int totalElements;
    private int totalPages;
    private int currentPage;
    private boolean ascending;
    private boolean empty;
}

package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.BrandDto;
import com.emazon.stock_service.application.dto.CustomPageDto;

public interface IBrandHandler {

    void saveBrand (BrandDto brandDto);

    CustomPageDto<BrandDto> getPaginatedCategories(Integer page, Integer pageSize, String order);
}

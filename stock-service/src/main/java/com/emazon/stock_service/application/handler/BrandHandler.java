package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.BrandDto;
import com.emazon.stock_service.application.dto.CustomPageDto;
import com.emazon.stock_service.application.mapper.IBrandDtoMapper;
import com.emazon.stock_service.application.mapper.IBrandPageDtoMapper;
import com.emazon.stock_service.application.mapper.ICustomPageDtoMapper;
import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.model.Brand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler {

    private final IBrandServicePort brandServicePort;
    private final IBrandDtoMapper brandDtoMapper;
    private final IBrandPageDtoMapper brandPageDtoMapper;

    @Override
    public void saveBrand(BrandDto brandDto) {
        Brand brand = brandDtoMapper.toBrand(brandDto);
        brandServicePort.save(brand);
    }

    @Override
    public CustomPageDto<BrandDto> getPaginatedCategories(Integer page, Integer pageSize, String order) {
        return brandPageDtoMapper.toCustomPageDto(brandServicePort.getPaginatedBrands(page,pageSize,order));
    }
}

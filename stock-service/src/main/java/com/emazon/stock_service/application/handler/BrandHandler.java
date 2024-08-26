package com.emazon.stock_service.application.handler;

import com.emazon.stock_service.application.dto.BrandDto;
import com.emazon.stock_service.application.mapper.IBrandDtoMapper;
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

    @Override
    public void saveBrand(BrandDto brandDto) {
        Brand brand = brandDtoMapper.toBrand(brandDto);
        brandServicePort.save(brand);
    }
}

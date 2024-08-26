package com.emazon.stock_service.infraestructure.configuration;

import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.usecase.BrandUseCase;
import com.emazon.stock_service.domain.usecase.CategoryUseCase;
import com.emazon.stock_service.infraestructure.output.jpa.adapter.BrandJpaAdapter;
import com.emazon.stock_service.infraestructure.output.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.IBrandEntityMapper;
import com.emazon.stock_service.infraestructure.output.jpa.mapper.ICategoryEntityMapper;
import com.emazon.stock_service.infraestructure.output.jpa.repository.IBrandRepository;
import com.emazon.stock_service.infraestructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    //For Category
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;


    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }


    //For Brand
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Bean IBrandPersistencePort brandPersistencePort() {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean IBrandServicePort brandServicePort() {
        return new BrandUseCase(brandPersistencePort());
    }


}

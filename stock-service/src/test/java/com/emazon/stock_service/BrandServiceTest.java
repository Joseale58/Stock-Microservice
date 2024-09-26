package com.emazon.stock_service;

import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.usecase.BrandUseCase;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandServiceTest {

    private IBrandServicePort brandUseCase;
    private IBrandPersistencePort brandPersistencePort;
    private Brand brand;

    @BeforeEach
    public void setUp() {
        brandPersistencePort = Mockito.mock(IBrandPersistencePort.class);
        brandUseCase = new BrandUseCase(brandPersistencePort);
    }

    //To show

    @Test
    void testGetPaginatedBrands_ValidParameters() {

        List<Brand> content = Arrays.asList(
                new Brand(1L, "Adidas", "Marca de Ropa"),
                new Brand(2L, "Nike", "Marca de Ropa"),
                new Brand(3L, "H&M", "Marca de Ropa")
        );

        CustomPage<Brand> mockPage = new CustomPage<>(
                content, 10L, 4, 0, false, false
        );

        when(brandPersistencePort.getPaginatedBrands(0, 10, "asc")).thenReturn(mockPage);

        CustomPage<Brand> result = brandUseCase.getPaginatedBrands(0, 10, "asc");

        assertNotNull(result);

        verify(brandPersistencePort, times(1)).getPaginatedBrands(0, 10, "asc");
    }

    @Test
    void testGetPaginatedBrands_InvalidPageNumber() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                brandUseCase.getPaginatedBrands(-1, 10, "asc"));
        assertEquals("El número de página debe ser mayor o igual a 0", exception.getMessage());
    }

    @Test
    void testGetPaginatedBrands_InvalidPageSize() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                brandUseCase.getPaginatedBrands(0, 0, "asc"));
        assertEquals("El tamaño de página debe ser mayor a 0 y menor o igual a 100", exception.getMessage());
    }

    @Test
    void testGetPaginatedBrands_InvalidOrder() {
        assertThrows(IllegalArgumentException.class, () ->
                brandUseCase.getPaginatedBrands(0, 10, "invalidOrder"));
    }

    // To save

    @Test
    void testSave_ValidBrand() {
        brand = new Brand(1L, "Valid Name", "Valid Description");
        brandUseCase.save(brand);
        verify(brandPersistencePort, times(1)).save(brand);
    }

    @Test
    void testSave_NullBrandName() {
        brand = new Brand(1L, null, "Valid Description");

        assertThrows(MissingValueException.class, () ->
                brandUseCase.save(brand));

    }

    @Test
    void testSave_EmptyBrandName() {
        brand = new Brand(1L, "", "Valid Description");

        assertThrows(MissingValueException.class, () ->
                brandUseCase.save(brand));

    }

    @Test
    void testSave_TooLongBrandName() {
        brand = new Brand(1L,"A".repeat(51), "Valid Description");

        assertThrows(DataConstraintViolationException.class, () ->
                brandUseCase.save(brand));

    }

    @Test
    void testSave_TooLongBrandDescription() {
        brand = new Brand(1L, "Valid Name", "A".repeat(121));
        assertThrows(DataConstraintViolationException.class, () ->
                brandUseCase.save(brand));
    }

}

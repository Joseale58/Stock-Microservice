package com.emazon.stock_service;

import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.usecase.BrandUseCase;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import com.emazon.stock_service.domain.usecase.BrandUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BrandServiceTest {

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

        // Crear la lista de marcas simulada
        List<Brand> content = Arrays.asList(
                new Brand(1L, "Adidas", "Marca de Ropa"),
                new Brand(2L, "Nike", "Marca de Ropa"),
                new Brand(3L, "H&M", "Marca de Ropa")
        );

        // Crea un objeto CustomPage simulado que será devuelto por el mock
        CustomPage<Brand> mockPage = new CustomPage<>(
                content, 10L, 4, 0, false, false
        );

        // Configura el mock para que cuando se llame a getPaginatedBrands con los parámetros específicos, retorne mockPage
        when(brandPersistencePort.getPaginatedBrands(0, 10, "asc")).thenReturn(mockPage);

        // Llama al método getPaginatedBrands con parámetros válidos
        CustomPage<Brand> result = brandUseCase.getPaginatedBrands(0, 10, "asc");

        assertNotNull(result); //Verifica que el resultado no sea nulo

        // Verifica que el método getPaginatedBrands del mock fue llamado exactamente una vez con los parámetros correctos
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
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                brandUseCase.getPaginatedBrands(0, 10, "invalidOrder"));
        assertEquals("El parámetro de orden debe ser 'asc' o 'desc'.", exception.getMessage());
    }

    // To save

    @Test
    void testSave_ValidBrand() {
        Brand brand = new Brand(1L, "Valid Name", "Valid Description");
        brandUseCase.save(brand);
        verify(brandPersistencePort, times(1)).save(brand);
    }

    @Test
    void testSave_NullBrandName() {
        Brand brand = new Brand(1L, null, "Valid Description");

        MissingValueException exception = assertThrows(MissingValueException.class, () ->
                brandUseCase.save(brand));

        assertEquals("nombre de marca no puede ser nulo", exception.getMessage());
    }

    @Test
    void testSave_EmptyBrandName() {
        Brand brand = new Brand(1L, "", "Valid Description");

        MissingValueException exception = assertThrows(MissingValueException.class, () ->
                brandUseCase.save(brand));

        assertEquals("nombre de marca no puede ser nulo", exception.getMessage());
    }

    @Test
    void testSave_TooLongBrandName() {
        Brand brand = new Brand(1L,"A".repeat(51), "Valid Description");

        DataConstraintViolationException exception = assertThrows(DataConstraintViolationException.class, () ->
                brandUseCase.save(brand));

        assertEquals("La longitud de nombre debe ser máximo de: 50 caracteres", exception.getMessage());
    }

    @Test
    void testSave_TooLongBrandDescription() {
        Brand brand = new Brand(1L, "Valid Name", "A".repeat(121));
        DataConstraintViolationException exception = assertThrows(DataConstraintViolationException.class, () ->
                brandUseCase.save(brand));
        assertEquals("La longitud de descripcion debe ser máximo de: 120 caracteres", exception.getMessage());
    }

}

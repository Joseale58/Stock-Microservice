package com.emazon.stock_service;

import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.usecase.CategoryUseCase;
import com.emazon.stock_service.domain.util.pageable.CustomPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private ICategoryPersistencePort categoryPersistencePort;
    private ICategoryServicePort categoryServicePort;
    private Category category;

    @BeforeEach
    public void setUp() {
        categoryPersistencePort = Mockito.mock(ICategoryPersistencePort.class);
        categoryServicePort = new CategoryUseCase(categoryPersistencePort);
        category = new Category(1L, "Nombre válido", "Descripción válida");
    }


    @Test
     void testGetCategoryByName_NameTooLong_ThrowsDataConstraintViolationException() {
        String longName = "Nombre que excede los cincuenta caracteres, por lo tanto, es inválido";

        assertThrows(IllegalArgumentException.class, () -> {
            categoryServicePort.getCategoryByName(longName);
        });

    }

    @Test
     void testGetCategoryByName_ValidName_ReturnsCategory() {
        String validName = "Nombre válido";
        when(categoryPersistencePort.getCategoryByName(validName)).thenReturn(category);

        Category result = categoryServicePort.getCategoryByName(validName);

        assertEquals(category, result);
        verify(categoryPersistencePort, times(1)).getCategoryByName(validName);
    }

    @Test
     void testGetAllCategories_ReturnsListOfCategories() {
        List<Category> categories = Arrays.asList(
                new Category(1L, "Categoria 1", "Descripción 1"),
                new Category(2L, "Categoria 2", "Descripción 2")
        );

        when(categoryPersistencePort.getAllCategories()).thenReturn(categories);

        List<Category> result = categoryServicePort.getAllCategories();

        assertEquals(categories.size(), result.size());
        verify(categoryPersistencePort, times(1)).getAllCategories();
    }

    @Test
     void testGetPaginatedCategories_InvalidPage_ThrowsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryServicePort.getPaginatedCategories(-1, 10, "asc");
        });

        assertEquals("El número de página debe ser mayor o igual a 0", exception.getMessage());
    }

    @Test
     void testGetPaginatedCategories_InvalidPageSize_ThrowsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryServicePort.getPaginatedCategories(0, 0, "asc");
        });

        assertEquals("El tamaño de página debe ser mayor a 0 y menor o igual a 100", exception.getMessage());
    }

    @Test
     void testGetPaginatedCategories_InvalidOrder_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            categoryServicePort.getPaginatedCategories(0, 10, "invalid_order");
        });

    }

    @Test
    void testGetPaginatedCategories_ValidParameters_ReturnsPaginatedCategories() {
        int page = 0;
        int pageSize = 10;
        String order = "asc";

        List<Category> content = Arrays.asList(
                new Category(1L, "Porcelana", "Elementos de porcelana"),
                new Category(7L, "Pollo", "Bla bla bla"),
                new Category(10L, "Pizzajasbdjkahsd", "Bla bla bla")
        );

        CustomPage<Category> paginatedCategories = new CustomPage<>(
                content, 10L, 4, 0, false, false
        );
        
        when(categoryPersistencePort.getPaginatedCategories(page, pageSize, order)).thenReturn(paginatedCategories);

        CustomPage<Category> result = categoryServicePort.getPaginatedCategories(page, pageSize, order);

        assertEquals(paginatedCategories, result);
        verify(categoryPersistencePort, times(1)).getPaginatedCategories(page, pageSize, order);
    }

    @Test
    void testSave_CategoryNameIsNull_ThrowsMissingValueException() {
        category = new Category(1L, null, "Descripción válida");

        assertThrows(MissingValueException.class, () -> {
            categoryServicePort.save(category);
        });

    }

    @Test
     void testSave_CategoryDescriptionIsNull_ThrowsMissingValueException() {
        category = new Category(1L, "Nombre válido", null);

        assertThrows(MissingValueException.class, () -> {
            categoryServicePort.save(category);
        });

    }

    @Test
    void testSave_CategoryNameTooLong_ThrowsDataConstraintViolationException() {
        category = new Category(1L, "Nombre que excede los cincuenta caracteres, por lo tanto, es inválido", "Descripción válida");

        assertThrows(DataConstraintViolationException.class, () -> {
            categoryServicePort.save(category);
        });

    }


    @Test
    void testSave_CategoryDescriptionTooLong_ThrowsDataConstraintViolationException() {
        category = new Category(1L, "Nombre válido", "Descripción que excede los noventa caracteres, por lo tanto, es inválida, y no debe ser aceptada por el sistema.");

        assertThrows(DataConstraintViolationException.class, () -> {
            categoryServicePort.save(category);
        });

    }

    @Test
    void testSave_ValidCategory_SavesCategory() {
        categoryServicePort.save(category);

        verify(categoryPersistencePort, times(1)).save(category);
    }

}

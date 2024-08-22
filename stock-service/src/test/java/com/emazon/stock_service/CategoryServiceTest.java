package com.emazon.stock_service;

import com.emazon.stock_service.domain.exception.DataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.api.ICategoryServicePort; // Asegúrate de que esta línea sea correcta

import com.emazon.stock_service.domain.usecase.CategoryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

//    private ICategoryServicePort categoryService;
//    private ICategoryServicePort categoryPersistencePort;
//
//    @BeforeEach
//    void setUp() {
//        categoryPersistencePort = mock(ICategoryServicePort.class);
//        categoryService = new ICategoryServicePort(categoryPersistencePort);
//    }

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
    public void testSave_CategoryNameIsNull_ThrowsMissingValueException() {
        // Crear un objeto Category con nombre nulo
        category = new Category(1L, null, "Descripción válida");

        Exception exception = assertThrows(MissingValueException.class, () -> {
            categoryServicePort.save(category);
        });

        assertEquals("El nombre de la categoría no puede ser nulo", exception.getMessage());
    }

    @Test
    public void testSave_CategoryDescriptionIsNull_ThrowsMissingValueException() {
        // Crear un objeto Category con descripción nula
        category = new Category(1L, "Nombre válido", null);

        Exception exception = assertThrows(MissingValueException.class, () -> {
            categoryServicePort.save(category);
        });

        assertEquals("La descripción de la categoría no puede ser nula", exception.getMessage());
    }

    @Test
    public void testSave_CategoryNameTooLong_ThrowsDataConstraintViolationException() {
        // Crear un objeto Category con nombre demasiado largo
        category = new Category(1L, "Nombre que excede los cincuenta caracteres, por lo tanto, es inválido", "Descripción válida");

        Exception exception = assertThrows(DataConstraintViolationException.class, () -> {
            categoryServicePort.save(category);
        });

        assertEquals("La longitud del nombre no puede ser mayor a 50 caracteres", exception.getMessage());
    }


    @Test
    public void testSave_CategoryDescriptionTooLong_ThrowsDataConstraintViolationException() {
        // Crear un objeto Category con descripción demasiado larga
        category = new Category(1L, "Nombre válido", "Descripción que excede los noventa caracteres, por lo tanto, es inválida, y no debe ser aceptada por el sistema.");

        Exception exception = assertThrows(DataConstraintViolationException.class, () -> {
            categoryServicePort.save(category);
        });

        assertEquals("La longitud de la descripción no puede ser mayor a 90 caracteres", exception.getMessage());
    }

    @Test
    public void testSave_ValidCategory_SavesCategory() {
        // Usar el objeto category ya inicializado en setUp
        categoryServicePort.save(category);

        verify(categoryPersistencePort, times(1)).save(category);
    }

}

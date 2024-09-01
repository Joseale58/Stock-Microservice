package com.emazon.stock_service;

import com.emazon.stock_service.domain.api.IProductServicePort;
import com.emazon.stock_service.domain.exception.InvalidProductCreationException;
import com.emazon.stock_service.domain.exception.MinimumDataConstraintViolationException;
import com.emazon.stock_service.domain.exception.MissingValueException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.spi.IProductPersistencePort;
import com.emazon.stock_service.domain.usecase.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    private  IProductPersistencePort productPersistencePort;
    private  ICategoryPersistencePort categoryPersistencePort;
    private IBrandPersistencePort brandPersistencePort;
    private  IProductServicePort productServicePort;
    private  Product product;
    private Brand brand;



    @BeforeEach
    public void setUp() {
        productPersistencePort = Mockito.mock(IProductPersistencePort.class);
        categoryPersistencePort = Mockito.mock(ICategoryPersistencePort.class);
        brandPersistencePort = Mockito.mock(IBrandPersistencePort.class);
        productServicePort = new ProductUseCase(productPersistencePort, categoryPersistencePort, brandPersistencePort);
        List<Category>categoryList = Arrays.asList(
                new Category(1L, "Categoría 1", "Descripción 1"),
                new Category(2L, "Categoría 2", "Descripción 2"),
                new Category(3L, "Categoría 3", "Descripción 3")
        );
        brand = new Brand(1L, "Nombre válido", "Descripción válida");
        product = new Product(1L, "Nombre válido", "Descripción válida", 10, 10D, brand, categoryList);
    }

    @Test
     void shouldThrowMissingValueExceptionWhenNameIsNull() {
        product.setName(null);

        assertThrows(MissingValueException.class, () -> productServicePort.save(product));
    }

    @Test
     void shouldThrowMissingValueExceptionWhenDescriptionIsEmpty() {
        product.setDescription("");

        assertThrows(MissingValueException.class, () -> productServicePort.save(product));
    }

    @Test
     void shouldThrowMissingValueExceptionWhenStockIsNull() {
        product.setStock(null);

        assertThrows(MissingValueException.class, () -> productServicePort.save(product));
    }

    @Test
     void shouldThrowMinimumDataConstraintViolationExceptionWhenStockIsNegative() {
        product.setStock(-1);

        assertThrows(MinimumDataConstraintViolationException.class, () -> productServicePort.save(product));
    }

    @Test
     void shouldThrowMissingValueExceptionWhenPriceIsNull() {
        product.setPrice(null);

        assertThrows(MissingValueException.class, () -> productServicePort.save(product));
    }

    @Test
     void shouldThrowMinimumDataConstraintViolationExceptionWhenPriceIsNegative() {
        product.setPrice(-1D);

        assertThrows(MinimumDataConstraintViolationException.class, () -> productServicePort.save(product));
    }

    @Test
    void shouldThrowMissingValueExceptionWhenBrandIsNull() {
        product.setBrand(brand);
        product.getBrand().setId(null);
        assertThrows(MissingValueException.class, () -> productServicePort.save(product));
    }

    @Test
    void shouldThrowInvalidProductCreationExceptionWhenBrandDoesNotExist() {
        when(brandPersistencePort.getBrandById(anyLong())).thenReturn(null);

        assertThrows(InvalidProductCreationException.class, () -> productServicePort.save(product));
    }

    @Test
     void shouldThrowInvalidProductCreationExceptionWhenCategoriesExceedLimit() {
        // Crear una lista con más de 3 categorías
        Category category1 = new Category(1L, "Category 1", "description");
        Category category2 = new Category(2L, "Category 2", "description");
        Category category3 = new Category(3L, "Category 3","description");
        Category category4 = new Category(4L, "Category 4", "description");

        product.setCategories(Arrays.asList(category1, category2, category3, category4));

        assertThrows(InvalidProductCreationException.class, () -> productServicePort.save(product));
    }

    @Test
     void shouldThrowInvalidProductCreationExceptionWhenCategoryIsDuplicate() {
        Category normalCategory = new Category(1L, "Categoría 1", "Descripción 1");
        Category duplicateCategory = new Category(1L, "Categoría 2", "Descripción 2");
        product.setCategories(Arrays.asList(normalCategory, duplicateCategory, duplicateCategory));

        assertThrows(InvalidProductCreationException.class, () -> productServicePort.save(product));
    }

    @Test
     void shouldThrowInvalidProductCreationExceptionWhenCategoryDoesNotExist() {
        when(categoryPersistencePort.getCategoryById(anyLong())).thenReturn(null);

        assertThrows(InvalidProductCreationException.class, () -> productServicePort.save(product));
    }
}

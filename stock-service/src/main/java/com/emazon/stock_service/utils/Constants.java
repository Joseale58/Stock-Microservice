package com.emazon.stock_service.utils;

public class Constants {

    private Constants() {
        throw new UnsupportedOperationException(UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED);
    }

    public static final String UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED = "Esta clase no deberías ser instanciada";

    //Messsages for exeptions
    public static final String EXCEPTION_MESSAGE = "Mensaje: ";

    //Usecases

    //Pagination
    public static final Integer PAGE_MINIMUM_INDEX = 0;
    public static final String PAGE_MUST_BE_POSITIVE_EXCEPTION = "El número de página debe ser mayor o igual a 0";

    public static final Integer PAGE_MIN_SIZE_ILLEGAL = 0;
    public static final Integer PAGE_MAX_SIZE = 100;
    public static final String PAGE_SIZE_RANGE_EXCEPTION = "El tamaño de página debe ser mayor a " + PAGE_MIN_SIZE_ILLEGAL +" y menor o igual a " + PAGE_MAX_SIZE;

    public static final String PAGE_ASC_OPTION = "asc";
    public static final String PAGE_DESC_OPTION = "desc";
    public static final String PAGE_ORDER_OPTION_EXCEPTION = "El parámetro de orden debe ser " + PAGE_ASC_OPTION + " o " + PAGE_DESC_OPTION + ".";

    public static final String PAGE_NAME_OPTION = "name";
    public static final String PAGE_BRAND_OPTION = "brand";
    public static final String PAGE_BRAND_SORT_OPTION = "brand.name";
    public static final String PAGE_CATEGORY_OPTION = "category";
    public static final String PAGE_CATEGORY_SORT_OPTION = "category.name";
    public static final String PAGE_SORT_OPTION_EXCEPTION = "El parámetro de ordenamiento debe ser 'name', 'brand' o 'category'.";
    //Brand
    public static final String MISSING_BRAND_NAME_EXCEPTION= "Nombre de marca no puede ser nulo o vacío";
    public static final String MISSING_BRAND_DESCRIPTION_EXCEPTION = "Descripción de marca no puede ser nulo o vacío";

    public static final int MAX_BRAND_NAME_LENGTH = 50;
    public static final String MAX_BRAND_NAME_LENGTH_EXCEPTION = "El nombre de la marca no puede tener más de 50 caracteres";

    public static final int MAX_BRAND_DESCRIPTION_LENGTH = 120;
    public static final String MAX_BRAND_DESCRIPTION_LENGTH_EXCEPTION = "La descripción de la marca no puede tener más de 120 caracteres";

    //Category
    public static final String MISSING_CATEGORY_NAME_EXCEPTION = "Nombre de categoría no puede ser nulo o vacío";
    public static final String MISSING_CATEGORY_DESCRIPTION_EXCEPTION = "Descripción de categoría no puede ser nulo o vacío";

    public static final int MAX_CATEGORY_NAME_LENGTH = 50;
    public static final String MAX_CATEGORY_NAME_LENGTH_EXCEPTION = "El nombre de la categoría no puede tener más de 50 caracteres";

    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 90;
    public static final String MAX_CATEGORY_DESCRIPTION_LENGTH_EXCEPTION = "La descripción de la categoría no puede tener más de 90 caracteres";

    //Product
    public static final String MISSING_PRODUCT_NAME_EXCEPTION = "Nombre de producto no puede ser nulo o vacío";
    public static final String MISSING_PRODUCT_DESCRIPTION_EXCEPTION = "Descripción de producto no puede ser nulo o vacío";
    public static final String MISSING_PRODUCT_STOCK_EXCEPTION = "Cantidad de producto no puede ser nulo";
    public static final String MISSING_PRODUCT_PRICE_EXCEPTION = "Precio de producto no puede ser nulo";
    public static final String MISSING_PRODUCT_BRAND_EXCEPTION = "Marca de producto no puede ser nulo";
    public static final String MISSING_PRODUCT_CATEGORIES_EXCEPTION = "Categorías de producto no puede ser nulo";

    public static final Integer INVALID_MINIMUM_STOCK_AMOUNT= 0;
    public static final String INVALID_MINIMUM_STOCK_AMOUNT_EXCEPTION = "La cantidad de stock debe ser mayor a cero";

    public static final Integer INVALID_MINIMUM_PRICE_AMOUNT = 0;
    public static final String INVALID_MINIMUM_PRICE_AMOUNT_EXCEPTION = "El precio no debe ser mayor a cero";

    public static final String BRAND_INVALID_ID_EXCEPTION = "No existe una marca con ese ID";
    public static final String CATEGORY_INVALID_AMOUNT_EXCEPTION = "El producto debe tener entre 1 y 3 categorías";
    public static final String CATEGORY_INVALID_ID_EXCEPTION = "No existe una categoría con ese ID";
    public static final String CATEGORY_DUPLICATED_EXCEPTION = "No se pueden repetir categorías en un producto";

    //Repository
    public static final String BRAND_ALREADY_EXISTS_EXCEPTION = "Ya existe una marca con ese nombre";
    public static final String CATEGORY_ALREADY_EXISTS_EXCEPTION = "Ya existe una categoría con ese nombre";
}


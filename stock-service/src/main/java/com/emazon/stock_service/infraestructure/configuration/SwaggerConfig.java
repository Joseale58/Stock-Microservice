package com.emazon.stock_service.infraestructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "API STOCK EMAZON",
                description = "This app provides an API REST as microservice to manage the Stock of The Company Emazon",
                termsOfService = "joserivillas.com",
                version = "1.0.0"
        )
)
public class SwaggerConfig {

}

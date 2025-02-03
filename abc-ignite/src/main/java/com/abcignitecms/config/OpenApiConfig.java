package com.abcignitecms.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Booking API",
        version = "v1",
        description = "APIs for managing bookings of club/gym classes"
    )
)
public class OpenApiConfig {
}

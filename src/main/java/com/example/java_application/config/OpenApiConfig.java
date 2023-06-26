package com.example.java_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().info(new Info()
            .title("User book API")
            .version("v1")
            .description("Users can retrieve and create books, which will be associated with their account. This API has been developed with extensive dependencies to facilitate testing and ensure robust functionality.")
        );
    }
}

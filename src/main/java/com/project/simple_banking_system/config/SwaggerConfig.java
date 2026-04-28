package com.project.simple_banking_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simple Banking System API")
                        .version("1.0")
                        .description("API para gerenciamento de contas bancárias e transações"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("banking-system")
                .packagesToScan("com.project.simple_banking_system.controller") // O caminho do seu controller
                .build();
    }
}
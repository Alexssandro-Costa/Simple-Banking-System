package com.project.simple_banking_system.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Classe de configuração para o Swagger/OpenAPI.
 * <p>
 * Esta classe define as configurações globais de documentação da API do Simple Banking System,
 * incluindo informações do sistema, agrupamento de endpoints e segurança baseada em JWT.
 * </p>
 *
 * @author Alexssandro
 * @version 1.0
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura as informações globais da API e define os esquemas de segurança.
     * <p>
     * Este método cria uma instância personalizada do {@link OpenAPI} definindo o título,
     * versão, descrição e a exigência de autenticação via token Bearer (JWT) para os endpoints.
     * </p>
     *
     * @return uma instância configurada de {@link OpenAPI}
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Configura os metadados visíveis no topo da página do Swagger UI
                .info(new Info()
                    .title("Simple Banking System API")
                    .version("3.0")
                    .description("API para gerenciamento de contas bancárias e transações"))

                // Define globalmente que a API requer um esquema de segurança chamado BearerAuth
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))

                // Registra o componente de segurança nos padrões do OpenAPI
                .components(new Components()
                    .addSecuritySchemes("BearerAuth", new SecurityScheme()
                        .name("BearerAuth") 
                        .type(SecurityScheme.Type.HTTP) // Define que é uma autenticação HTT
                        .scheme("bearer")               // Define o esquema como 'bearer'
                        .bearerFormat("JWT")));         // Especifica que o formato do token é JWT
    }

    /**
     * Configura o agrupamento dos endpoints públicos da API.
     * <p>
     * Restringe o escopo do Swagger para escanear apenas as classes controladoras (Controllers)
     * presentes no pacote especificado.
     * </p>
     *
     * @return uma instância configurada de {@link GroupedOpenApi}
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("banking-system")    // Nome do grupo que aparecerá no dropdown do Swagger UI
                // Escaneia apenas os pacotes dentro do pacote do controlador informado abaixo
                .packagesToScan("com.project.simple_banking_system.controller")
                .build();
    }
}
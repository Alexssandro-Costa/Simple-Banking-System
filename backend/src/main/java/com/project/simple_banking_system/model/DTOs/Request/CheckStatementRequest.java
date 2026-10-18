package com.project.simple_banking_system.model.DTOs.Request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CheckStatementRequest(
        @Schema(description = "número da página que deve ser buscada na paginação", example="0") int pageNumber,
        @Schema(description = "Quantidade de elementos que devem ser retornados", example="5") int quantity) {
} 
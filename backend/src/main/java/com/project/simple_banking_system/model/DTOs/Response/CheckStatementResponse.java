package com.project.simple_banking_system.model.DTOs.Response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CheckStatementResponse(
    @Schema(description = "Lista de transações") List<TransactionDTO> statement,
    @Schema(description = "Total de páginas presentes na paginação") int totalPages,
    @Schema(description = "Pagina utilizada é a primeira na paginação") boolean isFirst,
    @Schema(description = "Pagina utilizada é a ultima na paginação") boolean isLast) {
}

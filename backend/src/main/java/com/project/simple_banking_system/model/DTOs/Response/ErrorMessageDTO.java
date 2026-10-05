package com.project.simple_banking_system.model.DTOs.Response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorMessageDTO(
        @Schema(description = "Mensagem de erro legível pro usuário") String errorMessage) {
    
}

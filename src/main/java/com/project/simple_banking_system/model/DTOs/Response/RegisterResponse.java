package com.project.simple_banking_system.model.DTOs.Response;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterResponse(
        @Schema(description = "Contêm o token de acesso do usuário") AuthenticationResponse authenticationResponse) {
    
}
